package pastukh.vova.appcompose

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pastukh.vova.pdpapp.BuildConfig

val appModule = module {
    factory(named("server_url")) { BuildConfig.SERVER_URL }
}