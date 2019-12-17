// it is like JS -> require("react-player").default;
@file:JsModule("react-player")

import react.RClass
import react.RProps

@JsName("default")
external val ReactPlayer: RClass<ReactPlayerProps>

external interface ReactPlayerProps : RProps {
    var url: String
}