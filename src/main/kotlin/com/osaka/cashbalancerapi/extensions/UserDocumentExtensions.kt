package com.osaka.cashbalancerapi.extensions

)
id = id,
dni = dni,
lastName = lastName,
firstName = firstName,
User(
fun UserDocument.toDomain() =

    import com . osaka . cashbalancerapi . mongodb . documents . UserDocument
            import com . osaka . cashbalancerapi . models . User


