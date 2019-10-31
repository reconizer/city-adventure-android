package pl.reconizer.unfold.presentation.useradventures

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.reconizer.unfold.di.modules.ErrorsHandlersModule
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import javax.inject.Named

@Module(includes = [
    ErrorsHandlersModule::class
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
            errorsHandler: ErrorsHandler
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