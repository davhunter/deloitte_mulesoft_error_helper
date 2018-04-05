# MuleSoft Error Helper
This project provides common functionality for handling errors in RESTful, JSON-based APIs developed in **MuleSoft Anypoint**. The goals of this project are:

* To provide a standard, JSON-based response format for any errors returned from an API
* To provide each error with a unique number, to be logged, that can be used for troubleshooting. For example, returning `HTTP 500` with an error code of `-1` vs. `HTTP 500` with an error code of `-2`. This doesn't negate the practice of creating custom HTTP error codes, which can also be done, but ensures that, whether that is done or not, unique error codes are still returned.

In its current incarnation, errors are returned in JSON in the following format:

```
{
  "errorCode": -1,
  "message": "Standard message for the HTTP status code",
  "description": "full description of the error, which could include stacktrace";
}
```

This can, of course, be modified.

# Usage
This library isn't intended to be used directly from this GitHub repo; instead, the code should be downloaded and a fresh project created, to be used within a particular organization. (It is referenced from some other sample project here on GitHub, to illustrate its usage.) Once a new project has been created, based on this code, the JAR should be built, and deployed to a remote artifact repository.

In each project that wants to make use of it, developers should:

1. Include the JAR as a Maven dependency
1. Import the JAR's Mule Config: `<spring:beans><spring:import resource="classpath:deloitte_mulesoft_error_helper.xml"/></spring:beans>` (If the XML file is renamed, so should this inclusion be modified.)
1. In any new APIkit-based APIs created, edit the **Exception Strategy** to point to `deloitte_globalExceptionStrategy` (again, if this is renamed in the code, it should be renamed here)
1. The default Exception Strategy created by APIKit can be deleted

As new exceptions are needed, they should be added to the Error Helper, which should be rebuilt (and updated on the remote repo), making those errors available to any dependent applications.

## Adding a New Error
When a new error is needed -- i.e. when an API being built in Anypoint encounters a new error condition -- the following steps should be taken in the Error Helper library:

1. Create a new Java exception class
1. Update the `error-properties-properties` file, to add the new error and give it a unique number
1. Update the `deloitte_mulesoft_error_helper.xml` Mule Config, to add a new `<apikit:exception>` for the error, so that the appropriate HTTP status code is returned

The Java Exception to be created should be quite simple:
```
package any.package.can.be.used;

public class ExceptionName extends Exception {
    public ExceptionName(String message) {
        super(message);
    }
}
```

## Builds, and CI/CD
The intent is that any errors created across an organization would be centralized into this error helper, so that errors are unique across the organization. To streamline this properly, this library should be incorporated into the organization's CI/CD pipeline:

1. Code for the error helper is updated (e.g. a new exception is added) in SCM
1. CI/CD tool sees the change, builds the library, and deploys the JAR to the remote repo
1. CI/CD tool knows that there are other projects dependent on this one, so builds those, too, and redeploys them to their target Dev servers

Jenkins has this capability -- to build projects based on their dependencies having changed -- and other CI/CD tools likely do as well.
