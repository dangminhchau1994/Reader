package com.newspaper.chaudang.newspaper.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newspaper.chaudang.newspaper.Activity.WebViewThoiSu;
import com.newspaper.chaudang.newspaper.Activity.XMLDOMParser;
import com.newspaper.chaudang.newspaper.Adapter.ThoiSuAdapter;
import com.newspaper.chaudang.newspaper.Model.ThoiSu;
import com.newspaper.chaudang.newspaper.R;
import com.newspaper.chaudang.newspaper.Utils.CheckConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chau Dang on 8/1/2017.
 */

public class FragmentSucKhoe extends ListFragment {

    View view;
    RequestQueue requestQueue;
    ThoiSuAdapter thoiSuAdapter;
    String urlThoiSu = "http://vnexpress.net/rss/suc-khoe.rss";
    ArrayList<ThoiSu> thoisu = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_thoisu, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        readThoiSu();

        return view;
    }

    private void readThoiSu() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlThoiSu,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response != null) {

                            XMLDOMParser parser = new XMLDOMParser();
                            Document document = parser.getDocument(response);
                            NodeList nodeListDescription = document.getElementsByTagName("description");
                            NodeList nodeList = document.getElementsByTagName("item");

                            String hinhanh = "";
                            String title = "";
                            String link = "";

                            for (int i = 0; i < nodeList.getLength(); i++) {

                                String cdata = nodeListDescription.item(i + 1).getTextContent();
                                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                                Matcher matcher = p.matcher(cdata);

                                if (matcher.find()) {

                                    hinhanh = matcher.group(1);
                                    Log.d("hinhanh", hinhanh + "........." + i);

                                }

                                Element element = (Element) nodeList.item(i);
                                title  = parser.getValue(element, "title");
                                link = parser.getValue(element, "link");
                                thoisu.add(new ThoiSu(title, hinhanh, link));

                            }

                            thoiSuAdapter = new ThoiSuAdapter(getActivity(), thoisu);
                            setListAdapter(thoiSuAdapter);
                            thoiSuAdapter.notifyDataSetChanged();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast_short(getActivity(), "Xảy ra lỗi !!!");
                    }
                }
        );

        requestQueue.add(stringRequest);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(getActivity(), WebViewThoiSu.class);
        intent.putExtra("linkThoiSu", thoisu.get(position).getLink());
        intent.putExtra("tenThoiSu", thoisu.get(position).getTenThoiSu());
        intent.putExtra("HinhThoiSu", thoisu.get(position).getHinhanh());
        startActivity(intent);

        super.onListItemClick(l, v, position, id);
    }
}
