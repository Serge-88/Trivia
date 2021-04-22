package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

//==================================================================================================
//============================== Getting questions from JSON file ==================================
//==================================================================================================

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();
//    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    private String url = "https://raw.githubusercontent.com/Serge-88/JSON_for_my_app/main/Questions.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Question question = new Question();

                                // берём данные по индексу из JSONa, где нулевой индекс - вопрос, первый индекс - правильный ответ

                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                                questionArrayList.add(question);
                                Log.d("HI", "onResponse: " + question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (null != callback) callback.processFinished(questionArrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
