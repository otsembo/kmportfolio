package com.otsembo.portfolio.infrastructure.di

import com.otsembo.portfolio.business.viewmodels.LoginVM
import com.otsembo.portfolio.infrastructure.appModule
import com.otsembo.portfolio.infrastructure.desktopModule
import org.koin.dsl.module

fun desktopJVMdiModule() = listOf(appModule, desktopModule(), desktopUIModule())

fun desktopUIModule() =
    module {
        single<LoginVM> {
            LoginVM(useCase = get())
        }
    }
