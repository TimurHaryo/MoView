package com.timtam.uikit.recyclerview.resource

interface AdapterArgument<Arg> {

    var argument: Arg

    fun withArgument(lambda: Arg.() -> Unit) = argument.lambda()

    fun clearArgument() = Unit
}
