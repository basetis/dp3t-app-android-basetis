package ch.admin.bag.dp3t.language;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LanguageItem {
    private String code;
    private String language;

    public LanguageItem(String code, String language) {
        this.code = code;
        this.language = language;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageItem that = (LanguageItem) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, language);
    }

    @Override
    @NonNull
    public String toString() {
        return "LanguageItem{" +
                "code='" + code + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
