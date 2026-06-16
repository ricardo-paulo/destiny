package io.ricardo_paulo.CLI.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class InputNormalizer {

    private final String originalStr;

    public InputNormalizer (String originalStr) {
        this.originalStr = originalStr;
    }

    // Apenas retira todos os acentos do texto.
    public String getNormalized () {

        if (Normalizer.isNormalized(originalStr, Normalizer.Form.NFKD))
            return originalStr;

        String normalizedText = Normalizer.normalize(originalStr, Normalizer.Form.NFKD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");

        return pattern.matcher(normalizedText).replaceAll("");

    }

}
