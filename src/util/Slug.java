package util;

import java.text.Normalizer;

public class Slug
{
    public static final String from(String input)
    {
        if (input == null || input.isEmpty())
        { return ""; }

        // Normalize string (remove accents)
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");

        // Replace non-alphanumeric characters with hyphens
        String slug = normalized.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();

        // Replace spaces with hyphens
        slug = slug.replaceAll("\\s+", "-");

        // Remove leading and trailing hyphens
        slug = slug.replaceAll("^-+|-+$", "");

        return slug;
    }
}
