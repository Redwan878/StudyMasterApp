package com.porashona.studymaster.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00e0\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b*\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0019\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010YJ+\u0010Z\u001a\u00020W2\u0006\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\\2\b\b\u0002\u0010^\u001a\u00020\\H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010_J!\u0010`\u001a\u00020W2\u0006\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020LH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010dJ-\u0010e\u001a\u00020W2\u0006\u0010f\u001a\u00020b2\b\u0010g\u001a\u0004\u0018\u00010\\2\b\u0010h\u001a\u0004\u0018\u00010\\H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010iJ\u0019\u0010j\u001a\u00020W2\u0006\u0010k\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJ\u0019\u0010m\u001a\u00020W2\u0006\u0010n\u001a\u00020#H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010oJ\u0019\u0010p\u001a\u00020W2\u0006\u0010q\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010rJ\u0019\u0010s\u001a\u00020W2\u0006\u0010t\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010uJ\u0019\u0010v\u001a\u00020W2\u0006\u0010w\u001a\u00020+H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010xJ\u0019\u0010y\u001a\u00020W2\u0006\u0010z\u001a\u000201H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010{J\u0019\u0010|\u001a\u00020W2\u0006\u0010}\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010~J\u000f\u0010\u007f\u001a\u00020\\2\u0007\u0010\u0080\u0001\u001a\u00020bJ\u001e\u0010\u0081\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002070\u00190\u00182\t\b\u0002\u0010\u0082\u0001\u001a\u00020\\J\u001e\u0010\u0083\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00190\u00182\t\b\u0002\u0010\u0082\u0001\u001a\u00020\\J\u001c\u0010\u0084\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00190\u00182\u0007\u0010\u0085\u0001\u001a\u00020bJ%\u0010\u0086\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00190\u00182\u0007\u0010\u0087\u0001\u001a\u00020b2\u0007\u0010\u0088\u0001\u001a\u00020bJ\u0010\u0010\u0089\u0001\u001a\u00020b2\u0007\u0010\u008a\u0001\u001a\u00020bJ\u001c\u0010\u008b\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00190\u00182\u0007\u0010\u0085\u0001\u001a\u00020bJ\u001d\u0010\u008c\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030\u008d\u00010\u00190\u00182\u0007\u0010\u008e\u0001\u001a\u00020bJ\u001c\u0010\u008f\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00190\u00182\u0007\u0010\u0085\u0001\u001a\u00020bJ\u0013\u0010\u0090\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u0018J\u0015\u0010\u0091\u0001\u001a\u0004\u0018\u00010.H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0092\u0001J\u001f\u0010\u0093\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030\u0094\u00010\u00190\u00182\t\b\u0002\u0010\u0095\u0001\u001a\u00020LJ\u001c\u0010\u0096\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u00190\u00182\u0007\u0010\u0085\u0001\u001a\u00020bJ\u001d\u0010\u0097\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u00190\u00182\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001J\u001c\u0010\u009a\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u00182\u0007\u0010\u009b\u0001\u001a\u00020bJ\u001c\u0010\u009c\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u00182\u0007\u0010\u0085\u0001\u001a\u00020bJ\u0013\u0010\u009d\u0001\u001a\u00020WH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0092\u0001J\u0013\u0010\u009e\u0001\u001a\u00020WH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0092\u0001J\u001a\u0010\u009f\u0001\u001a\u00020b2\u0006\u0010n\u001a\u00020#H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010oJ\u001a\u0010\u00a0\u0001\u001a\u00020b2\u0006\u0010q\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010rJ\u001a\u0010\u00a1\u0001\u001a\u00020b2\u0006\u0010t\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010uJ\u001a\u0010\u00a2\u0001\u001a\u00020b2\u0006\u0010w\u001a\u00020+H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010xJ\u001a\u0010\u00a3\u0001\u001a\u00020b2\u0006\u0010z\u001a\u000201H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010{J\u001a\u0010\u00a4\u0001\u001a\u00020b2\u0006\u0010}\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010~J)\u0010\u00a5\u0001\u001a\u00020W2\u0007\u0010\u00a6\u0001\u001a\u00020\\2\u000b\b\u0002\u0010\u00a7\u0001\u001a\u0004\u0018\u00010bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00a8\u0001J\u001a\u0010\u00a9\u0001\u001a\u00020W2\u0006\u0010X\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010YJ\u001c\u0010\u00aa\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00190\u00182\u0007\u0010\u00ab\u0001\u001a\u00020\\J\u001c\u0010\u00ac\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u00190\u00182\u0007\u0010\u00ab\u0001\u001a\u00020\\J\t\u0010\u00ad\u0001\u001a\u00020\\H\u0002J&\u0010\u00ae\u0001\u001a\u00020W2\u0007\u0010\u00a6\u0001\u001a\u00020\\2\b\u0010\u00af\u0001\u001a\u00030\u00b0\u0001H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00b1\u0001J&\u0010\u00b2\u0001\u001a\u00020W2\u0007\u0010\u00a6\u0001\u001a\u00020\\2\b\u0010\u00b3\u0001\u001a\u00030\u00b0\u0001H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00b1\u0001J\u001b\u0010\u00b4\u0001\u001a\u00020W2\u0007\u0010\u00b5\u0001\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJ\u001b\u0010\u00b6\u0001\u001a\u00020W2\u0007\u0010\u00b7\u0001\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJ\u001b\u0010\u00b8\u0001\u001a\u00020W2\u0007\u0010\u00b9\u0001\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJ\u001a\u0010\u00ba\u0001\u001a\u00020W2\u0006\u0010k\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJ\u001a\u0010\u00bb\u0001\u001a\u00020W2\u0006\u0010X\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010YJ%\u0010\u00bc\u0001\u001a\u00020W2\u0007\u0010\u00bd\u0001\u001a\u00020\\2\u0007\u0010\u00be\u0001\u001a\u00020LH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00bf\u0001J\u001a\u0010\u00c0\u0001\u001a\u00020W2\u0006\u0010n\u001a\u00020#H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010oJ\u001a\u0010\u00c1\u0001\u001a\u00020W2\u0006\u0010q\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010rJ#\u0010\u00c2\u0001\u001a\u00020W2\u0006\u0010f\u001a\u00020b2\u0007\u0010\u00c3\u0001\u001a\u00020LH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010dJ\u001a\u0010\u00c4\u0001\u001a\u00020W2\u0006\u0010t\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010uJ\u001a\u0010\u00c5\u0001\u001a\u00020W2\u0006\u0010w\u001a\u00020+H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010xJ\u001a\u0010\u00c6\u0001\u001a\u00020W2\u0006\u0010z\u001a\u000201H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010{J\u001a\u0010\u00c7\u0001\u001a\u00020W2\u0006\u0010}\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010~J\u001b\u0010\u00c8\u0001\u001a\u00020W2\u0007\u0010\u00b9\u0001\u001a\u00020bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lR\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001cR\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001cR\u001d\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001cR\u001d\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001cR\u001d\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001cR\u001d\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001cR\u001d\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001cR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002070\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u001cR\u001d\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010\u001cR\u001d\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010\u001cR\u001d\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010\u001cR\u000e\u0010?\u001a\u00020@X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010\u001cR\u001d\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010\u001cR\u001d\u0010E\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010\u001cR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u001cR\u0017\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010\u001cR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010N\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010L0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010\u001cR\u001d\u0010P\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u001cR\u001d\u0010R\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010\u001cR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010T\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010\u001c\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u00c9\u0001"}, d2 = {"Lcom/porashona/studymaster/data/repository/ExtendedRepository;", "", "goalDao", "Lcom/porashona/studymaster/data/dao/GoalDao;", "taskDao", "Lcom/porashona/studymaster/data/dao/TaskDao;", "noteDao", "Lcom/porashona/studymaster/data/dao/NoteDao;", "examDao", "Lcom/porashona/studymaster/data/dao/ExamDao;", "challengeDao", "Lcom/porashona/studymaster/data/dao/ChallengeDao;", "blockedAppDao", "Lcom/porashona/studymaster/data/dao/BlockedAppDao;", "quoteDao", "Lcom/porashona/studymaster/data/dao/QuoteDao;", "resourceDao", "Lcom/porashona/studymaster/data/dao/StudyResourceDao;", "eventDao", "Lcom/porashona/studymaster/data/dao/AcademicEventDao;", "userProfileDao", "Lcom/porashona/studymaster/data/dao/UserProfileDao;", "(Lcom/porashona/studymaster/data/dao/GoalDao;Lcom/porashona/studymaster/data/dao/TaskDao;Lcom/porashona/studymaster/data/dao/NoteDao;Lcom/porashona/studymaster/data/dao/ExamDao;Lcom/porashona/studymaster/data/dao/ChallengeDao;Lcom/porashona/studymaster/data/dao/BlockedAppDao;Lcom/porashona/studymaster/data/dao/QuoteDao;Lcom/porashona/studymaster/data/dao/StudyResourceDao;Lcom/porashona/studymaster/data/dao/AcademicEventDao;Lcom/porashona/studymaster/data/dao/UserProfileDao;)V", "activeBlockedApps", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/porashona/studymaster/data/model/BlockedApp;", "getActiveBlockedApps", "()Lkotlinx/coroutines/flow/Flow;", "activeGoals", "Lcom/porashona/studymaster/data/model/Goal;", "getActiveGoals", "allBlockedApps", "getAllBlockedApps", "allEvents", "Lcom/porashona/studymaster/data/model/AcademicEvent;", "getAllEvents", "allExams", "Lcom/porashona/studymaster/data/model/Exam;", "getAllExams", "allGoals", "getAllGoals", "allNotes", "Lcom/porashona/studymaster/data/model/Note;", "getAllNotes", "allQuotes", "Lcom/porashona/studymaster/data/model/Quote;", "getAllQuotes", "allResources", "Lcom/porashona/studymaster/data/model/StudyResource;", "getAllResources", "allTasks", "Lcom/porashona/studymaster/data/model/Task;", "getAllTasks", "completedChallenges", "Lcom/porashona/studymaster/data/model/Challenge;", "getCompletedChallenges", "completedExams", "getCompletedExams", "completedTasks", "getCompletedTasks", "customQuotes", "getCustomQuotes", "dateFormat", "Ljava/text/SimpleDateFormat;", "favoriteNotes", "getFavoriteNotes", "favoriteQuotes", "getFavoriteQuotes", "favoriteResources", "getFavoriteResources", "holidays", "getHolidays", "pendingTasks", "getPendingTasks", "pendingTasksCount", "", "getPendingTasksCount", "totalBlockAttempts", "getTotalBlockAttempts", "upcomingEvents", "getUpcomingEvents", "upcomingExams", "getUpcomingExams", "whitelistedApps", "getWhitelistedApps", "addBlockedApp", "", "app", "(Lcom/porashona/studymaster/data/model/BlockedApp;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addCustomQuote", "textEn", "", "textBn", "author", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addMinutesToGoal", "goalId", "", "minutes", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "completeExam", "examId", "result", "reflection", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "completeTask", "taskId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEvent", "event", "(Lcom/porashona/studymaster/data/model/AcademicEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteExam", "exam", "(Lcom/porashona/studymaster/data/model/Exam;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteGoal", "goal", "(Lcom/porashona/studymaster/data/model/Goal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNote", "note", "(Lcom/porashona/studymaster/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteResource", "resource", "(Lcom/porashona/studymaster/data/model/StudyResource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTask", "task", "(Lcom/porashona/studymaster/data/model/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "formatCountdown", "milliseconds", "getDailyChallenges", "date", "getDailyGoals", "getEventsBySubject", "subjectId", "getEventsInRange", "startDate", "endDate", "getExamCountdown", "examDate", "getExamsBySubject", "getMostBlockedApps", "Lcom/porashona/studymaster/data/dao/AppBlockCount;", "since", "getNotesBySubject", "getOverdueTasks", "getRandomQuote", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentBlockStats", "Lcom/porashona/studymaster/data/model/BlockStatistic;", "limit", "getResourcesBySubject", "getResourcesByType", "type", "Lcom/porashona/studymaster/data/model/ResourceType;", "getSubtasks", "parentId", "getTasksBySubject", "initializeDailyChallenges", "initializeQuotes", "insertEvent", "insertExam", "insertGoal", "insertNote", "insertResource", "insertTask", "recordBlockAttempt", "packageName", "sessionId", "(Ljava/lang/String;Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeBlockedApp", "searchNotes", "query", "searchResources", "todayDate", "toggleAppBlocked", "isBlocked", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleAppWhitelisted", "isWhitelisted", "toggleNoteFavorite", "noteId", "toggleQuoteFavorite", "quoteId", "toggleResourceFavorite", "resourceId", "uncompleteTask", "updateBlockedApp", "updateChallengeProgress", "challengeId", "value", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateEvent", "updateExam", "updateExamProgress", "progress", "updateGoal", "updateNote", "updateResource", "updateTask", "visitResource", "app_debug"})
public final class ExtendedRepository {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.GoalDao goalDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.TaskDao taskDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.NoteDao noteDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.ExamDao examDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.ChallengeDao challengeDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.BlockedAppDao blockedAppDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.QuoteDao quoteDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.StudyResourceDao resourceDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.AcademicEventDao eventDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.UserProfileDao userProfileDao = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> allGoals = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> activeGoals = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> allTasks = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> pendingTasks = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> completedTasks = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> pendingTasksCount = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> allNotes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> favoriteNotes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> allExams = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> upcomingExams = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> completedExams = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Challenge>> completedChallenges = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> allBlockedApps = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> activeBlockedApps = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> whitelistedApps = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalBlockAttempts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> allQuotes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> favoriteQuotes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> customQuotes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> allResources = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> favoriteResources = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> allEvents = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> upcomingEvents = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> holidays = null;
    
    public ExtendedRepository(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.GoalDao goalDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.TaskDao taskDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.NoteDao noteDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.ExamDao examDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.ChallengeDao challengeDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.BlockedAppDao blockedAppDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.QuoteDao quoteDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.StudyResourceDao resourceDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.AcademicEventDao eventDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.UserProfileDao userProfileDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getAllGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getActiveGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getDailyGoals(@org.jetbrains.annotations.NotNull
    java.lang.String date) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertGoal(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateGoal(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteGoal(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addMinutesToGoal(long goalId, int minutes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getAllTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getPendingTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getCompletedTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getPendingTasksCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getSubtasks(long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getTasksBySubject(long subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getOverdueTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object completeTask(long taskId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object uncompleteTask(long taskId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getAllNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getFavoriteNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getNotesBySubject(long subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> searchNotes(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertNote(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateNote(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleNoteFavorite(long noteId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getAllExams() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getUpcomingExams() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getCompletedExams() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getExamsBySubject(long subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertExam(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateExam(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteExam(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateExamProgress(long examId, int progress, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object completeExam(long examId, @org.jetbrains.annotations.Nullable
    java.lang.String result, @org.jetbrains.annotations.Nullable
    java.lang.String reflection, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final long getExamCountdown(long examDate) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Challenge>> getDailyChallenges(@org.jetbrains.annotations.NotNull
    java.lang.String date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Challenge>> getCompletedChallenges() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeDailyChallenges(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateChallengeProgress(@org.jetbrains.annotations.NotNull
    java.lang.String challengeId, int value, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getAllBlockedApps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getActiveBlockedApps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getWhitelistedApps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalBlockAttempts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addBlockedApp(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateBlockedApp(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object removeBlockedApp(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleAppBlocked(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, boolean isBlocked, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleAppWhitelisted(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, boolean isWhitelisted, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object recordBlockAttempt(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.Nullable
    java.lang.Long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockStatistic>> getRecentBlockStats(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.dao.AppBlockCount>> getMostBlockedApps(long since) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getAllQuotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getFavoriteQuotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Quote>> getCustomQuotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeQuotes(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getRandomQuote(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Quote> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addCustomQuote(@org.jetbrains.annotations.NotNull
    java.lang.String textEn, @org.jetbrains.annotations.NotNull
    java.lang.String textBn, @org.jetbrains.annotations.NotNull
    java.lang.String author, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleQuoteFavorite(long quoteId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getAllResources() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getFavoriteResources() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getResourcesBySubject(long subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getResourcesByType(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.ResourceType type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> searchResources(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertResource(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateResource(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteResource(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object visitResource(long resourceId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleResourceFavorite(long resourceId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getAllEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getUpcomingEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getHolidays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getEventsInRange(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getEventsBySubject(long subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertEvent(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateEvent(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteEvent(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String todayDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String formatCountdown(long milliseconds) {
        return null;
    }
}