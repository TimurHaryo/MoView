package com.timtam.uikit.recyclerview.resourceful

interface AdapterArgument<Arg> {

    var argument: Arg

    fun withArgument(lambda: Arg.() -> Unit) = argument.lambda()

    fun clearArgument() = Unit
}
