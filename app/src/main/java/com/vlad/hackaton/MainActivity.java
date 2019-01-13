package com.vlad.hackaton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";

    Button[][] cells = new Button[6][6];

    int startX = 20, startY = 50, step = 80;

    TableLayout field;

    int minesCount = 5;
    ArrayList<Pair<Integer, Integer>> mines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mines.add(new Pair<Integer, Integer>(1, 1));
        mines.add(new Pair<Integer, Integer>(2, 2));
        mines.add(new Pair<Integer, Integer>(3, 3));
        mines.add(new Pair<Integer, Integer>(4, 4));
        mines.add(new Pair<Integer, Integer>(5, 5));

        field = (TableLayout) findViewById(R.id.field);

        boolean switcher = false;
        for (int i = 0; i < 6; i++) {

            TableRow row = new TableRow(this);

            for (int j = 0; j < 6; j++) {
                Button cell = new Button(this);

                if (switcher)
                    cell.setBackgroundColor(Color.GREEN);
                else
                    cell.setBackgroundColor(Color.BLUE);

                switcher = !switcher;

                row.addView(cell, new TableRow.LayoutParams(step, step));

                cell.setId((i + 1) * 10 + (j + 1));

                final int _i = i, _j = j;

                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Clicked " + v.getId());

                        ((Button) v).setEnabled(false);

                        for (Pair pos : mines) {
                            int id = (Integer) pos.first * 10 + (Integer) pos.second;

                            Log.d(TAG, "Mine on " + id);

                            if (v.getId() == id) {
                                ((Button) v).setBackgroundColor(Color.RED);

                                Log.d(TAG, "Detonate on " + id);

                                for (Pair fail : mines) {
                                    cells[(Integer) fail.first - 1][(Integer) fail.second - 1].
                                            setBackgroundColor(Color.RED);
                                }

                                for (Button[] row_ : cells) {
                                    for (Button btn : row_) {
                                        btn.setEnabled(false);
                                    }
                                }

                                return;
                            }
                        }


                        ((Button) v).setBackgroundColor(Color.GRAY);

                        Log.d(TAG, "Free on " + v.getId());

                        dpsRequest(_i + 1, _j + 1);

                    }
                });

                cells[i][j] = cell;
            }

            switcher = !switcher;

            row.setX(startX);

            field.addView(row, TableLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void dpsRequest(int x, int y) {
        Log.d(TAG, "dps on " + x + " " + y);

        if (x >= 1 && x <= 6 && y >= 1 && y <= 6 && cells[x][y].isEnabled()) {
            for (Pair mine : mines) {
                if (x != (Integer) mine.first && y != (Integer) mine.second) {
                    cells[x - 1][y - 1].setBackgroundColor(Color.GRAY);
                    cells[x - 1][y -1 ].setEnabled(false);
                }
            }
        }
        else return;

        dpsRequest(x - 1, y - 1);
        dpsRequest(x, y - 1);
        dpsRequest(x + 1, y - 1);

        dpsRequest(x - 1, y);
        dpsRequest(x + 1, y);

        dpsRequest(x - 1, y + 1);
        dpsRequest(x, y + 1);
        dpsRequest(x + 1, y + 1);

    }
}
