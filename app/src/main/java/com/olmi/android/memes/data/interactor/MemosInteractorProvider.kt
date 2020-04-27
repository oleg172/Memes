package com.olmi.android.memes.data.interactor

import com.olmi.android.memes.data.repo.MemesRepoProvider

object MemosInteractorProvider {
    val MEMES_INTERACTOR: MemesInteractor by lazy { MemesInteractor(MemesRepoProvider.memesRepo) }
}
