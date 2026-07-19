package com.porashona.studymaster.data.backup

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

class GoogleDriveManager(private val context: Context) {
    private val TAG = "GoogleDriveManager"

    // Drive scopes for read/write access
    private val SCOPE = Drive.SCOPE_FILE_FULL

    // Backup folder name
    private val BACKUP_FOLDER_NAME = "StudyMaster_Backups"
    private var driveService: Drive? = null

    data class BackupMetadata(
        val fileId: String,
        val timestamp: Long,
        val version: Int,
        val appVersion: String,
        val fileSize: Long
    )

    fun initializeDriveClient(): Boolean {
        try {
            // Initialize Drive service
            val jsonFactory = AndroidJsonFactory()
            driveService = Drive.Builder(
                context,
                com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential.from(context),
                null
            ).setApplicationName("StudyMasterApp")
                .build()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Drive client", e)
            return false
        }
    }

    fun createBackupFolder(): Task<File?> {
        return Tasks.call { DriveHelper.createFolder(driveService, BACKUP_FOLDER_NAME) }
    }

    fun uploadBackupFile(folderId: String, fileName: String, data: ByteArray): Task<String> {
        return Tasks.call {
            // Upload to Google Drive
            val fileMetadata = File().apply {
                this.name = fileName
                this.parents = listOf(folderId)
                this.mimeType = "application/octet-stream"
            }

            val mediaContent = com.google.api.services.drive.model.MediaContent(data.java.lang.ByteArrayInputStream())
            val uploadedFile = driveService?.files()?.create(fileMetadata, mediaContent)?.execute()

            uploadedFile?.id ?: throw Exception("Upload failed - no file ID returned")
        }
    }

    fun downloadBackupFile(fileId: String): ByteArray {
        return Tasks.call {
            driveService?.files()?.get(fileId)?.execute()?.let { file ->
                driveService?.files()?.get(fileId, com.google.api.services.drive.Drive.Files.GetFields("download?")).execute()
            } ?: throw Exception("Failed to download backup file")
        }
    }

    fun listBackupFiles(): Task<List<BackupMetadata>> {
        return Tasks.call {
            val query = DriveHelper.buildQuery("mimeType='application/octet-stream' and name contains 'studymaster_backup_'")
            val fileList = driveService?.files()?.list(query)?.execute() as? FileList

            fileList?.files?.map { file ->
                BackupMetadata(
                    fileId = file.id ?: "",
                    timestamp = file.createdTime?.value?.toLongOrNull() ?: 0,
                    version = extractVersionFromFilename(file.name ?: ""),
                    appVersion = extractAppVersionFromFilename(file.name ?: ""),
                    fileSize = file.size?.toLongOrNull() ?: 0
                )
            }?.filter { it.fileId.isNotEmpty() } ?: emptyList()
        }
    }

    fun deleteBackupFile(fileId: String): Task<Void> {
        return Tasks.call { driveService?.files()?.delete(fileId)?.execute() }
    }

    private fun extractVersionFromFilename(filename: String): Int {
        val regex = "_v(\d+)".toRegex()
        val match = regex.find(filename)
        return match?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0
    }

    private fun extractAppVersionFromFilename(filename: String): String {
        val regex = "_app(\d+\.\d+\.\d+)".toRegex()
        val match = regex.find(filename)
        return match?.groupValues?.getOrNull(1) ?: "1.0.0"
    }
}

object DriveHelper {
    fun createFolder(driveService: Drive?, folderName: String): File {
        if (driveService == null) {
            throw IllegalStateException("Drive service not initialized")
        }

        // Check if folder exists
        val query = buildQuery("name='${folderName}' and mimeType='application/vnd.google-apps.folder'")
        val fileList = driveService.files().list(query).execute()

        fileList.files?.forEach { existingFile ->
            if (existingFile.name == folderName) {
                return existingFile
            }
        }

        // Create new folder
        val folderMetadata = File().apply {
            name = folderName
            mimeType = "application/vnd.google-apps.folder"
        }

        return driveService.files().create(folderMetadata).execute()
    }

    fun buildQuery(filter: String): String {
        val escapedFilter = filter.replace("'", "\\'")
        return "'$escapedFilter' in parents and trashed=false"
    }

    fun exportDatabaseToJson(context: Context, databasePath: String): ByteArray {
        try {
            val outputStream = ByteArrayOutputStream()
            // This would need to handle actual database export
            // For now, just create a placeholder structure
            val jsonTemplate = """{
                "timestamp": ${System.currentTimeMillis()},
                "appVersion": "1.0.0",
                "database": {
                    // Database would be exported here
                },
                "sharedPrefs": {
                    // Shared preferences would be exported here
                }
            }"""
            outputStream.write(jsonTemplate.toByteArray(Charsets.UTF_8))
            return outputStream.toByteArray()
        } catch (e: Exception) {
            Log.e("DriveHelper", "Failed to export database", e)
            throw e
        }
    }

    fun parseBackupData(jsonData: String): DriveBackupData {
        // Parse JSON backup data
        // This would contain actual database and preferences parsing logic
        return DriveBackupData()
    }
}

data class DriveBackupData(
    val timestamp: Long = 0,
    val appVersion: String = "",
    val appPackage: String = "",
    val databaseData: ByteArray? = null,
    val sharedPrefsData: ByteArray? = null
)