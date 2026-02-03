package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0011\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\u0012\u001a\u00020\u00032\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J#\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u0017\u001a\u00020\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001e"}, d2 = {"Lcom/porashona/studymaster/data/dao/QuoteDao;", "", "delete", "", "quote", "Lcom/porashona/studymaster/data/model/Quote;", "(Lcom/porashona/studymaster/data/model/Quote;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQuotes", "Lkotlinx/coroutines/flow/Flow;", "", "getCustomQuotes", "getFavoriteQuotes", "getQuotesCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRandomQuote", "insert", "", "insertAll", "quotes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsShown", "id", "time", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setFavorite", "isFavorite", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
@androidx.room.Dao
public abstract interface QuoteDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Quote quote, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.porashona.studymaster.data.model.Quote> quotes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Quote quote, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Quote quote, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quotes ORDER BY shownCount ASC, RANDOM() LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRandomQuote(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Quote> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY id DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getFavoriteQuotes();
    
    @androidx.room.Query(value = "SELECT * FROM quotes WHERE isCustom = 1 ORDER BY id DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getCustomQuotes();
    
    @androidx.room.Query(value = "SELECT * FROM quotes ORDER BY id")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getAllQuotes();
    
    @androidx.room.Query(value = "UPDATE quotes SET isFavorite = :isFavorite WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavorite(long id, boolean isFavorite, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE quotes SET shownCount = shownCount + 1, lastShownAt = :time WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsShown(long id, long time, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quotes")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getQuotesCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}