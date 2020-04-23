package com.olmi.android.memes.data.interactor

import com.olmi.android.memes.data.repo.MemesRepoProvider

object MemosInteractorProvider {
    val memosInteractor: MemosInteractor by lazy { MemosInteractor(MemesRepoProvider.memesRepo) }
}
