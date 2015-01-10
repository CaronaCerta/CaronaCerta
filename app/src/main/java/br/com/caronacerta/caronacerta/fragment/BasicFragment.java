package br.com.caronacerta.caronacerta.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;

public abstract class BasicFragment extends Fragment {
    /**
     * Method for Setting the Height of the ListView dynamically.
     * Hack to fix the issue of not showing all the items of the ListView when placed inside a ScrollView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ActionBar.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // adding more 10 to remove scroll
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 10;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setExpandedListViewHeightBasedOnChildren(
            ExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null, listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height += appendHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setCollapseListViewHeightBasedOnChildren(ExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null, listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height -= appendHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToLoginActivity() {
        navigateToActivity(LoginActivity.class);
    }

    public void navigateToActivity(Class<?> cls) {
        Intent loginActivity = new Intent(getActivity().getApplicationContext(), cls);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginActivity);
    }
}
