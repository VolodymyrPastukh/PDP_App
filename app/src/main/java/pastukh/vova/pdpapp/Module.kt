package pastukh.vova.pdpapp

import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    factory(named("server_url")) { BuildConfig.SERVER_URL }
}