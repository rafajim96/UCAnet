package com.pdm.ucanet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.SessionManager;


public class ProfileFragment extends Fragment {
    private User loggedUser;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getContext());
        loggedUser = sessionManager.loadSession();

        TextView userText = (TextView) view.findViewById(R.id.nameText);
        userText.setText(loggedUser.getName());
        TextView careerText = (TextView) view.findViewById(R.id.textCareer);
        careerText.setText(loggedUser.getCareer());
        TextView idText = (TextView) view.findViewById(R.id.textId);
        idText.setText(loggedUser.getUsername());
        return view;
    }
}
