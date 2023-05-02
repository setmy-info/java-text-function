package info.setmy.textfunctions.register;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Short.parseShort;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public class KeywordFunctionMapper {

    public static KeywordFunctionMapper newInstance(final Namespace namespace) {
        final KeywordFunctionMapper keywordFunctionMapper = new KeywordFunctionMapper(namespace);
        keywordFunctionMapper.put("character", string -> string.toCharArray()[0]);
        // TODO : value parsing should be for boolean language agnostic (Jah/EI/Tõsi/Vale)
        keywordFunctionMapper.put("boolean", string -> parseBoolean(string));
        keywordFunctionMapper.put("short", string -> parseShort(string));
        keywordFunctionMapper.put("int", string -> parseInt(string));
        keywordFunctionMapper.put("long", string -> parseLong(string));
        keywordFunctionMapper.put("float", string -> parseFloat(string));
        keywordFunctionMapper.put("double", string -> parseDouble(string));
        //TODO : formatting can differ on different platforms for bigDecimal
        keywordFunctionMapper.put("bigDecimal", string -> new BigDecimal(string));
        keywordFunctionMapper.put("string", string -> string);
        keywordFunctionMapper.put("time", TypeFunctions.TIME_TRANSFORM_FUNCTION);
        keywordFunctionMapper.put("date", TypeFunctions.DATE_TRANSFORM_FUNCTION);
        keywordFunctionMapper.put("dateTime", TypeFunctions.DATE_TIME_TRANSFORM_FUNCTION);
        keywordFunctionMapper.put("list", TypeFunctions.LIST_TRANSFORM_FUNCTION);
        keywordFunctionMapper.put("set", TypeFunctions.SET_TRANSFORM_FUNCTION);
        // TODO : detect - type by investigated properties

        keywordFunctionMapper.register("character", "char", "Char");
        keywordFunctionMapper.register("boolean", "bool", "Boolean", "Bool");
        keywordFunctionMapper.register("int", "Int", "Integer", "integer");

        return keywordFunctionMapper;
    }

    private final Map<String, Function<String, Object>> keywordFunctions = new ConcurrentHashMap<>();

    private final Namespace namespace;

    private final List<Synonym> synonyms = new ArrayList<>();

    private final Set<String> allKeywords = new HashSet<>();

    public KeywordFunctionMapper(final Namespace namespace) {
        this.namespace = namespace;
    }

    public void put(final String key, final Function<String, Object> func) {
        keywordFunctions.put(key, func);
    }

    public Optional<Function<String, Object>> getFunction(final String key) {
        return ofNullable(get(key));
    }

    public Function<String, Object> get(final String key) {
        final Function<String, Object> result = keywordFunctions.get(getKeywordBySynonym(key).orElse(key));
        return result;
    }

    public boolean register(final String keyword, final String... synonyms) {
        return this.synonyms.add(new Synonym(keyword, asList(synonyms)));
    }

    public boolean register(final String keyword, final List<String> synonyms) {
        return this.synonyms.add(new Synonym(keyword, synonyms));
    }

    public <T> Optional<T> convert(final String keyword, final String value) {
        return getFunction(keyword)
            .map(stringObjectFunction -> (T) stringObjectFunction.apply(value));
    }

    public Set<String> getAllKeywordPlaceholders() {
        return getAllKeywords().stream()
            .map(s -> "{" + s + "}")
            .collect(toSet());
    }

    public Set<String> getAllKeywords() {
        if (!allKeywords.isEmpty()) {
            return unmodifiableSet(allKeywords);
        }
        final Set<String> keywords = getSynonymKeywords();
        final Set<String> moreKeywords = keywordFunctions.entrySet().stream()
            .map(stringFunctionEntry -> stringFunctionEntry.getKey())
            .collect(toSet());
        allKeywords.addAll(keywords);
        allKeywords.addAll(moreKeywords);
        return unmodifiableSet(allKeywords);
    }

    public Optional<String> getKeywordBySynonym(final String synonymString) {
        return synonyms.stream()
            .filter(synonym -> synonym.containsKey(synonymString))
            .findFirst()
            .map(synonym -> synonym.getKey());
    }

    public Set<String> getSynonymKeywords() {
        final Set<String> keywords = synonyms.stream()
            .map(synonym -> synonym.getKey())
            .collect(toSet());
        synonyms.forEach(synonym -> keywords.addAll(synonym.getValue()));
        return keywords;
    }
}
