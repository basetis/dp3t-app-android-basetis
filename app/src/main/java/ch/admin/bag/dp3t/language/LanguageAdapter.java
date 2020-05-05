package ch.admin.bag.dp3t.language;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.admin.bag.dp3t.R;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private MutableLiveData<Integer> selectedItem = new MutableLiveData<>();
    private MutableLiveData<List<LanguageItem>> listLanguages = new MutableLiveData<>();
    private MutableLiveData<String> currentLang = new MutableLiveData<>();
    private LiveData<OnLanguageSelected> onLanguageSelected;

    public LanguageAdapter(@NonNull List<LanguageItem> list, String currentLang, OnLanguageSelected onLanguageSelected) {
        this.listLanguages.setValue(list);
        this.currentLang.setValue(currentLang);
        this.onLanguageSelected = new MutableLiveData<>(onLanguageSelected);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equalsIgnoreCase(currentLang)) {
                selectedItem.setValue(i);
                break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listLanguages.getValue() != null) {
            holder.bind(listLanguages.getValue().get(position).getLanguage(), position);
        }
    }

    @Override
    public int getItemCount() {
        if (listLanguages.getValue() != null)
            return listLanguages.getValue().size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Integer position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(v -> {
                selectedItem.setValue(position);
                textView.setSelected(position.equals(selectedItem.getValue()));
                notifyDataSetChanged();
                if (onLanguageSelected.getValue() != null && listLanguages.getValue() != null) {
                    onLanguageSelected.getValue().onLanguageSelected(listLanguages.getValue().get(position));
                }
            });
        }

        public void bind(String value, Integer position) {
            textView.setText(value);
            this.position = position;
            if (position.equals(selectedItem.getValue())) {
                textView.setTextAppearance(R.style.NextStep_Text_Bold_Blue);
            } else {
                textView.setTextAppearance(R.style.NextStep_Text);
            }
        }
    }

    public interface OnLanguageSelected {
        public void onLanguageSelected(LanguageItem languageItem);
    }
}
