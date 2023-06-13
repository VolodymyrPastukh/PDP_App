package pastukh.vova.utils

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module


val moduleUtils = module {
    single(named(DispatcherToken.IO)) { Dispatchers.IO }
    single(named(DispatcherToken.DEFAULT)) { Dispatchers.Default }
    single(named(DispatcherToken.MAIN)) { Dispatchers.Main }
}

enum class DispatcherToken {
    IO,
    DEFAULT,
    MAIN,
}