package info.setmy.textfunctions

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

@SuppressFBWarnings(
    value = ["EI_EXPOSE_REP", "EI_EXPOSE_REP2"],
    justification = "Trying to avoid null pointer errors, so value should exist."
)
class Example {

    String firstName
}
