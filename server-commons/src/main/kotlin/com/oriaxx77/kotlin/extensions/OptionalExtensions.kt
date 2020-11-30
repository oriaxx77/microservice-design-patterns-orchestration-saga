package com.oriaxx77.kotlin.extensions




fun <T> T?.isNone(): Boolean = (this == null)

fun <T> T?.isSome(): Boolean = (this != null)




/**
 * Usage: val some = none.orDefault( default )
 */
fun <T> T?.orDefault(default:T ): T = this ?: default


/**
 * Usage  val some = none.orElse( fn )
 */
fun <T> T?.orElse( elseFn: () -> T ) = this ?: elseFn()


/**
 * Usage: val some = none.orThrow( RuntimeException() )
 */
fun <T> T?.orThrow(exception: () -> Throwable): T {
    if ( this == null )
        throw exception()

    return this
}


/**
 * Usage: val some = none.orTry( fn ).orTry( fn )
 */
fun <T> T?.orTry( tryFn: () -> T? ) = this ?: tryFn()


//inline infix fun <T> (() -> T).andThrow(crossinline throwable: T.() -> Throwable): () -> Nothing = andThen { throw throwable() }


fun <T,E> T?.flatMap(mapFn: (T) -> E? ) = this?.let(mapFn)

fun <T,E> T?.mapOrElse( mapFn: (T) -> E, default: E ) =  this?.let(mapFn) ?: default

fun <T,E> T?.mapOrElse( mapFn: (T) -> E, elseFn: () -> E ) = this?.let(mapFn) ?: elseFn()

fun <T,E> T?.map2(mapFn: (T) -> E ) = this?.let(mapFn)

fun <T> T?.on( onFn: (T) -> Unit) {
    if (this != null){onFn(this)}
}

//func map<T>(_ fn: (Wrapped) throws -> T, default: T) rethrows -> T {
//    return try map(fn) ?? `default`
//    }


//
//
//fun map2( )
//{
//
//}
//
//
//
//func map2(_ f: (Wrapped) -> Void) {
//    if let wrapped = self { f(wrapped) }
//}
//
//func should(_ do:() throws -> Void ) -> Error? {
//    do{
//        try `do`()
//            return nil
//        } catch let error {
//            return error
//        }
//    }
// TODO: implement this
/*
func should(_ do:(Wrapped) throws -> Void ) -> Error? {
    do{
        // try `do`(self)
        return nil
    } catch let error {
        return error
    }
}*/



//
//inline fun <T, R> T?.letOrElse(nullBlock: () -> R, block: (T) -> R): R = this?.let(block) ?: nullBlock()
//


//// OR
//
//inline fun <T, R> T?.letOrElse(nullBlock: () -> R, block: (T) -> R): R = this?.let(block) ?: nullBlock()




/*
    // MARK - map2

    // E.g. should { try throwingFunction) }.orDefault(print($0))
    func map2(_ f: (Wrapped) -> Void) {
        if let wrapped = self { f(wrapped) }
    }

    func should(_ do:() throws -> Void ) -> Error? {
        do{
            try `do`()
            return nil
        } catch let error {
            return error
        }
    }
    // TODO: implement this
    /*
    func should(_ do:(Wrapped) throws -> Void ) -> Error? {
        do{
            // try `do`(self)
            return nil
        } catch let error {
            return error
        }
    }*/


    // MARK - map

    func map<T>(_ fn: (Wrapped) throws -> T, default: T) rethrows -> T {
        return try map(fn) ?? `default`
    }

    func map<T>(_ fn: (Wrapped) throws -> T, else: () throws -> T) rethrows -> T {
        return try map(fn) ?? `else`()
    }

    // MARK - filter

    /// Returns the unwrapped value of the optional only if
    /// - The optional has a value
    /// - The value satisfies the predicate `predicate`

    // E.g. users.each( seller.filter( hasAdminRole ).map2( admins.add ) )
    func filter(_ predicate: (Wrapped) -> Bool) -> Wrapped? {
        guard let unwrapped = self,
            predicate(unwrapped) else { return nil }
        return self
    }

    // E.g. seller.fulfill( isAdmin )
    func fulfill( _ condition: (Wrapped) -> Bool ) -> Bool {
        guard let unwrapped = self,
            condition(unwrapped ) else { return false }
        return true
    }

    /*
     func filter(_ predicate: (Wrapped) -> Bool) -> Optional<Wrapped> {
     return map(predicate) == .some(true) ? self : .none
     }
     */

    /// Returns the wrapped value orDefault crashes with `fatalError(message)`
    func expect(_ message: String) -> Wrapped {
        guard let value = self else { fatalError(message) }
        return value
    }

    // MARK combining optionals

    /// Tries to unwrap `self` and if that succeeds continues to unwrap the parameter `optional`
    /// and returns the result of that.


    // E.g. a.and( B() )
    func and<B>(_ optional: B?) -> B? {
        guard self != nil else { return nil }
        return optional
    }

    /// Executes a closure with the unwrapped result of an optional.
    /// This allows chaining optionals together.

    // E.g. seller.and( map2: {(seller) in emailService.sendEmailTo( seller )}  )
    func and<T>(map2: (Wrapped) throws -> T?) rethrows -> T? {
        guard let unwrapped = self else { return nil }
        return try map2(unwrapped)
    }

    /// Zips the content of this optional with the content of another
    /// optional `other` only if both optionals are not empty

    // E.g.: myTuple = a.zip2( with: b ) // (a,b)
    func zip2<A>(with other: Optional<A>) -> (Wrapped, A)? {
        guard let first = self, let second = other else { return nil }
        return (first, second)
    }

    /// Zips the content of this optional with the content of another
    /// optional `other` only if both optionals are not empty
    func zip3<A, B>(with other: Optional<A>, another: Optional<B>) -> (Wrapped, A, B)? {
        guard let first = self,
            let second = other,
            let third = another else { return nil }
        return (first, second, third)
    }

    // TODO: zipN with monads

    // MARK on

    func on( none f: () throws -> Void) rethrows {
        if isNone { try f() }
    }

    func on( some f: () throws -> Void) rethrows {
        if isSome { try f() }
    }

    func fold( someF: () throws -> Void,
               noneF: () throws -> Void ) rethrows {
        if isSome { try someF()}
        else { try noneF() }
    }

}

extension Optional where Wrapped == Error {
    /// Only perform `else` if the optional has a non-empty error value
    func orDefault(_ else: (Error) -> Void) {
        guard let error = self else { return }
        `else`(error)
    }
}

extension Optional where Wrapped == String {
    var isEmpty: Bool {
        return self?.isEmpty ?? true
    }

    var notEmpty: Bool {
        return !isEmpty
    }
}

 */