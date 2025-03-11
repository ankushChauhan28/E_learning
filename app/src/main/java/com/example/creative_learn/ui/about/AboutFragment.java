package com.example.creative_learn.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.creative_learn.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        setupButtons(root);

        return root;
    }

    private void setupButtons(View root) {
        Button contactSupportButton = root.findViewById(R.id.contactSupportButton);
        Button submitFeedbackButton = root.findViewById(R.id.submitFeedbackButton);
        Button faqButton = root.findViewById(R.id.faqButton);

        contactSupportButton.setOnClickListener(v -> showContactSupport());
        submitFeedbackButton.setOnClickListener(v -> showFeedbackDialog());
        faqButton.setOnClickListener(v -> openFAQ());
    }

    private void showContactSupport() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:support@creativelearn.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void showFeedbackDialog() {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.submit_feedback)
            .setItems(new String[]{"Report a Bug", "Suggest a Feature", "General Feedback"}, (dialog, which) -> {
                String subject = "";
                switch (which) {
                    case 0:
                        subject = "Bug Report";
                        break;
                    case 1:
                        subject = "Feature Suggestion";
                        break;
                    case 2:
                        subject = "General Feedback";
                        break;
                }
                sendFeedbackEmail(subject);
            })
            .show();
    }

    private void sendFeedbackEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:feedback@creativelearn.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(intent, "Send Feedback"));
    }

    private void openFAQ() {
        // Replace with your actual FAQ URL
        String faqUrl = "https://creativelearn.com/faq";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(faqUrl));
        startActivity(intent);
    }
} 