package com.example.creative_learn.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.creative_learn.MainActivity;
import com.example.creative_learn.R;
import com.example.creative_learn.utils.LocaleHelper;
import com.example.creative_learn.utils.ThemeHelper;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
    private Spinner languageSpinner;
    private RadioGroup themeRadioGroup;
    private Map<String, String> languageCodes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        setupLanguageSettings(root);
        setupThemeSettings(root);

        return root;
    }

    private void setupLanguageSettings(View root) {
        languageSpinner = root.findViewById(R.id.languageSpinner);
        Button applyButton = root.findViewById(R.id.applyLanguageButton);

        // Setup language options
        languageCodes = new HashMap<>();
        languageCodes.put("English", "en");
        languageCodes.put("Hindi", "hi");
        languageCodes.put("Spanish", "es");
        languageCodes.put("French", "fr");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            languageCodes.keySet().toArray(new String[0])
        );

        languageSpinner.setAdapter(adapter);

        // Set current language
        String currentLang = LocaleHelper.getLanguage(requireContext());
        for (Map.Entry<String, String> entry : languageCodes.entrySet()) {
            if (entry.getValue().equals(currentLang)) {
                int position = adapter.getPosition(entry.getKey());
                languageSpinner.setSelection(position);
                break;
            }
        }

        applyButton.setOnClickListener(v -> changeLanguage());
    }

    private void changeLanguage() {
        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        String langCode = languageCodes.get(selectedLanguage);
        
        LocaleHelper.setLocale(requireContext(), langCode);
        Toast.makeText(requireContext(), "Language changed to " + selectedLanguage, Toast.LENGTH_SHORT).show();
        
        // Restart app to apply changes
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        requireActivity().finish();
    }

    private void setupThemeSettings(View root) {
        themeRadioGroup = root.findViewById(R.id.themeRadioGroup);
        
        // Set the current theme selection
        String currentTheme = ThemeHelper.getTheme(requireContext());
        switch (currentTheme) {
            case "light":
                themeRadioGroup.check(R.id.lightThemeRadio);
                break;
            case "dark":
                themeRadioGroup.check(R.id.darkThemeRadio);
                break;
            default:
                themeRadioGroup.check(R.id.systemThemeRadio);
                break;
        }

        themeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String theme;
            if (checkedId == R.id.lightThemeRadio) {
                theme = "light";
            } else if (checkedId == R.id.darkThemeRadio) {
                theme = "dark";
            } else {
                theme = "system";
            }
            
            ThemeHelper.setTheme(requireContext(), theme);
            requireActivity().recreate();
        });
    }
} 