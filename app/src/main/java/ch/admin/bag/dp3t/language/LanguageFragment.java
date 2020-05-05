package ch.admin.bag.dp3t.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.admin.bag.dp3t.R;

public class LanguageFragment extends Fragment implements LanguageAdapter.OnLanguageSelected {
    public static Fragment newInstance() {
        return new LanguageFragment();
    }

    public LanguageFragment() {
        super(R.layout.fragment_language);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.language_toolbar);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = view.findViewById(R.id.language_recycler_view);

        List<LanguageItem> languageItems = null;
        try {
            languageItems = getLanguageItems(getResources().obtainTypedArray(R.array.languages));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(new LanguageAdapter(languageItems, getLanguage(getContext()), this));
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * @param obtainTypedArray
     * @return
     * @see "https://stackoverflow.com/a/40137782"
     */
    private List<LanguageItem> getLanguageItems(TypedArray obtainTypedArray) throws IllegalAccessException {
        int size = obtainTypedArray.length();
        List<LanguageItem> itemList = new ArrayList<>(size);

        TypedArray countryTypedArray = null;

        for (int i = 0; i < size; i++) {
            int resId = obtainTypedArray.getResourceId(i, -1);
            if (i >= 0) {
                countryTypedArray = getResources().obtainTypedArray(resId);
                itemList.add(new LanguageItem(countryTypedArray.getString(0),
                        countryTypedArray.getString(1)));
            } else {
                throw new IllegalAccessException(("The language xml is not valid"));
            }
        }

        if (obtainTypedArray != null) obtainTypedArray.recycle();
        if (countryTypedArray != null) countryTypedArray.recycle();

        return itemList;
    }

    private void updateLanguage(Context ctx, String lang) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("locale", lang).apply();
        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang)) {
            cfg.locale = new Locale(lang);
        } else {
            cfg.locale = Locale.getDefault();
        }

        ctx.getResources().updateConfiguration(cfg, null);
    }

    private String getLanguage(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString("locale", Locale.getDefault().getLanguage());
    }

    @Override
    public void onLanguageSelected(LanguageItem languageItem) {
        updateLanguage(getContext(), languageItem.getCode());
    }
}
