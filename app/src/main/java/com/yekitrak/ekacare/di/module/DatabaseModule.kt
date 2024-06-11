package com.yekitrak.ekacare.di.module

import android.app.Application
import androidx.room.Room
import com.yekitrak.ekacare.data.constant.Constants
import com.yekitrak.ekacare.data.db.AppDatabase
import com.yekitrak.ekacare.data.db.UserUseCase
import com.yekitrak.ekacare.data.db.UserUseCaseImpl
import com.yekitrak.ekacare.data.db.dao.UserDao
import com.yekitrak.ekacare.data.repository.UserRepository
import com.yekitrak.ekacare.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        Constants.DB_NAME
    ).build()

    @Provides
    internal fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao
    }

    @Provides
    fun provideUsersRepository(usersDao: UserDao): UserRepository {
        return UserRepositoryImpl(usersDao) as UserRepository
    }

    @Provides
    @Singleton
    fun provideUsersUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }
}