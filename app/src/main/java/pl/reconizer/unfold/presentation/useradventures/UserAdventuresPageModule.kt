package pl.reconizer.unfold.presentation.useradventures

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.modules.ErrorHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorHandlersModule::class
])
class UserAdventuresPageModule(
        private val adventuresType: UserAdventuresType
) {

    @Provides
    @ViewScope
    fun providePresenter(
            @Named("background") backgroundScheduler: Scheduler,
            @Named("main") mainScheduler: Scheduler,
            userRepository: IUserRepository,
            errorsHandler: ErrorsHandler<Error>
    ): UserAdventuresPresenter {
        return UserAdventuresPresenter(
                backgroundScheduler,
                mainScheduler,
                userRepository,
                errorsHandler,
                adventuresType
        )
    }

    @Provides
    @ViewScope
    fun provideAdapter(): UserAdventuresAdapter {
        return UserAdventuresAdapter()
    }

}