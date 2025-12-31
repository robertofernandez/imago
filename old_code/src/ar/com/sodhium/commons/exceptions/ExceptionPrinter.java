package ar.com.sodhium.commons.exceptions;

/**
 * Class to print exceptions to make them legible.
 * 
 * @author Roberto Fernandez
 * 
 */
public final class ExceptionPrinter {

    private ExceptionPrinter() {
    }

    public static String printException(final Throwable exception) {
        String output = "[Exception of class " + exception.getClass().getName() + ": " + exception.getMessage() + ", {";
        int i = 0;
        for (StackTraceElement element : exception.getStackTrace()) {
            if (i++ > 0) {
                output += ", ";
            }
            output += element.toString();
        }

        if (exception.getCause() != null) {
            output += "Cause: " + ExceptionPrinter.printException(exception.getCause());
        }
        output += "}]";
        return output;
    }
}
