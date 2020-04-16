package com.olmi.android.memes.data.repo

object MemesRepoProvider {
    val memesRepo: MemesRepo by lazy { MemesRepoImpl() }
}