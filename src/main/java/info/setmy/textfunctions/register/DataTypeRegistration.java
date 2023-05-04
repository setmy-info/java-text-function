package info.setmy.textfunctions.register;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DataTypeRegistration {

    private Optional<String> optionalNamespaceName;

    private Optional<String> optionalKeyword;

    private Optional<Function<String, Object>> optionalFunction;

    @Default
    @SuppressFBWarnings(
        value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "Trying to avoid null pointer errors, so value should exist."
    )
    private List<String> synonymsList = new ArrayList<>();

    public static class DataTypeRegistrationBuilder {

        public DataTypeRegistrationBuilder namespaceName(final String namespaceName) {
            this.optionalNamespaceName = ofNullable(namespaceName);
            return this;
        }

        public DataTypeRegistrationBuilder keyword(final String keyword) {
            this.optionalKeyword = ofNullable(keyword);
            return this;
        }

        public DataTypeRegistrationBuilder function(final Function<String, Object> function) {
            this.optionalFunction = ofNullable(function);
            return this;
        }

        public DataTypeRegistrationBuilder synonyms(final String... synonyms) {
            this.synonymsList$value = unmodifiableList(asList(synonyms));
            return this;
        }
    }
}
