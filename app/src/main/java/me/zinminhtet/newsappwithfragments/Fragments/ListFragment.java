package me.zinminhtet.newsappwithfragments.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import me.zinminhtet.newsappwithfragments.Classes.NewsItem;
import me.zinminhtet.newsappwithfragments.ContentActivity;
import me.zinminhtet.newsappwithfragments.R;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        new AsyncTask<Void, Void, List<NewsItem>>() {
            @Override
            protected List<NewsItem> doInBackground(Void... params) {
                return NewsItem.jread();
            }

            @Override
            protected void onPostExecute(List<NewsItem> result) {
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                        result, android.R.layout.simple_list_item_2,
                        new String[]{"title", "area"},
                        new int[]{android.R.id.text1, android.R.id.text2});
                ListView list = view.findViewById(R.id.listView);
                list.setAdapter(adapter);
                list.setOnItemClickListener(ListFragment.this);
            }
        }.execute();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        NewsItem item = (NewsItem) av.getAdapter().getItem(position);
        String url = item.get("url");
        if (getActivity().findViewById(R.id.contentframe) == null) {
            // single-pane
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } else
            // multi-pane
            display(url);
    }

    void display(String url) {
        final String TAG = "DETAILS_FRAG";
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();

        Fragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        if (fm.findFragmentByTag(TAG) == null)
            // fragment not found -- to be added
            trans.add(R.id.contentframe, fragment, TAG);
        else
            // fragment found -- to be replaced
            trans.replace(R.id.contentframe, fragment, TAG);
        trans.commit();
    }

}
