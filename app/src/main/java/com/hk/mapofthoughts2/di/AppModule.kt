package com.hk.mapofthoughts2.di

import android.app.Application
import androidx.room.Room
import com.hk.mapofthoughts2.data.NoteDataBase
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import com.hk.mapofthoughts2.domain.repository.NoteRepositoryImpl
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.AudioPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//We can inject this into view model so that they can use it? okok, I see maybe these kidn of thingk is simpler than I thought
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(app,NoteDataBase::class.java,NoteDataBase.DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

//    @Provides
//    @Singleton
//    fun provideAudioPlayer(app: Application): AudioPlayer {
//        return AudioPlayer(app)
//    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

//    @Provides
//    @Singleton
//    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
//        return NoteUseCases(
//            getNotesUseCase = GetNotesUseCase(repository),
//            deleteNoteUseCase = DeleteNoteUseCase(repository),
//        addNote = AddNote(repository),
//            getNoteUseCase = GetNoteUseCase(repository)
//
//        )
//    }
}