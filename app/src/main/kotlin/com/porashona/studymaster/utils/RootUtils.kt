package com.porashona.studymaster.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

object RootUtils {

    /**
     * Check if device has root access
     */
    suspend fun isRootAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("id\n")
            os.writeBytes("exit\n")
            os.flush()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = reader.readLine()

            process.waitFor()
            reader.close()
            os.close()

            output?.contains("uid=0") == true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Execute shell command with root
     */
    suspend fun executeRootCommand(command: String): CommandResult = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)

            os.writeBytes("$command\n")
            os.writeBytes("exit\n")
            os.flush()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))

            val output = StringBuilder()
            val error = StringBuilder()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            while (errorReader.readLine().also { line = it } != null) {
                error.append(line).append("\n")
            }

            val exitCode = process.waitFor()

            reader.close()
            errorReader.close()
            os.close()

            CommandResult(
                success = exitCode == 0,
                output = output.toString().trim(),
                error = error.toString().trim(),
                exitCode = exitCode
            )
        } catch (e: Exception) {
            CommandResult(
                success = false,
                output = "",
                error = e.message ?: "Unknown error",
                exitCode = -1
            )
        }
    }

    /**
     * Force stop an app using root
     */
    suspend fun forceStopApp(packageName: String): Boolean {
        val result = executeRootCommand("am force-stop $packageName")
        return result.success
    }

    /**
     * Disable an app using root
     */
    suspend fun disableApp(packageName: String): Boolean {
        val result = executeRootCommand("pm disable $packageName")
        return result.success
    }

    /**
     * Enable an app using root
     */
    suspend fun enableApp(packageName: String): Boolean {
        val result = executeRootCommand("pm enable $packageName")
        return result.success
    }

    /**
     * Hide an app using root
     */
    suspend fun hideApp(packageName: String): Boolean {
        val result = executeRootCommand("pm hide $packageName")
        return result.success
    }

    /**
     * Unhide an app using root
     */
    suspend fun unhideApp(packageName: String): Boolean {
        val result = executeRootCommand("pm unhide $packageName")
        return result.success
    }

    /**
     * Kill app process using root
     */
    suspend fun killApp(packageName: String): Boolean {
        val result = executeRootCommand("am kill $packageName")
        return result.success
    }

    /**
     * Clear app data using root
     */
    suspend fun clearAppData(packageName: String): Boolean {
        val result = executeRootCommand("pm clear $packageName")
        return result.success
    }

    data class CommandResult(
        val success: Boolean,
        val output: String,
        val error: String,
        val exitCode: Int
    )
}