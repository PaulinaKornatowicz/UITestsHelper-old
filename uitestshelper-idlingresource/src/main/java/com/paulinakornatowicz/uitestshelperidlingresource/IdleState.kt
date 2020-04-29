package com.paulinakornatowicz.uitestshelperidlingresource

import java.io.Serializable

class Busy(val message: String): IdleState

class Idle(val message: String): IdleState

interface IdleState: Serializable