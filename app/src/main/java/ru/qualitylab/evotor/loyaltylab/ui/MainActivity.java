package ru.qualitylab.evotor.loyaltylab.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.evotor.framework.core.IntegrationAppCompatActivity;
import ru.evotor.framework.core.IntegrationManagerFuture;
import ru.evotor.framework.core.action.command.open_receipt_command.OpenSellReceiptCommand;
import ru.evotor.framework.core.action.event.receipt.changes.position.PositionAdd;
import ru.evotor.framework.navigation.NavigationApi;
import ru.evotor.framework.receipt.Receipt;
import ru.evotor.framework.receipt.ReceiptApi;
import ru.qualitylab.evotor.loyaltylab.R;
import ru.qualitylab.evotor.loyaltylab.api.RecommendationApi;
import ru.qualitylab.evotor.loyaltylab.api.Retrofitik;
import ru.qualitylab.evotor.loyaltylab.model.ProductUi;
import ru.qualitylab.evotor.loyaltylab.util.ChangesCreator;
import ru.qualitylab.evotor.loyaltylab.util.Logger;
import ru.qualitylab.evotor.loyaltylab.util.Mapper;

public class MainActivity extends IntegrationAppCompatActivity {

    private Activity context;
    private RecommendationAdapter adapter;

    private Button addButton;
    private ProgressBar progressBar;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        progressBar = findViewById(R.id.progress_recommendation);
        setToolbar();
        initRv();
        initAddBtn();
        initRecommendationLoading();
    }

    private void initRecommendationLoading() {
        Receipt receipt = ReceiptApi.getReceipt(this, Receipt.Type.SELL);
        if (receipt != null) {
            Logger.log("Download started!");
            compositeDisposable.add(Retrofitik.getInstanse()
                    .create(RecommendationApi.class)
                    .getRecommendations(Mapper.createUidBodyFromReceipt(receipt.getPositions()))
                    .doOnSubscribe(l -> showLoading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(this::hideLoading)
                    .subscribe(recommendations -> updateRv(getFakeDataset()),
                            error -> {
                                Snackbar.make(addButton, "Произошла ошибка!", Snackbar.LENGTH_SHORT).show();
                                Logger.log(error.getMessage());
                            }));
        } else {
            Snackbar.make(addButton, "Отсутствуют товары в чеке!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateRv(List<ProductUi> productList) {
        if (productList.size() > 0) {
            adapter.setDataset(productList);
            adapter.notifyDataSetChanged();
            showAddBtn();
        }
    }

    private void showAddBtn() {
        ViewCompat.animate(addButton)
                .alpha(1.0f)
                .setDuration(300)
                .start();
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initAddBtn() {
        addButton = findViewById(R.id.btn_recommendation_add);
        addButton.setOnClickListener(view -> {
            List<PositionAdd> list = ChangesCreator.createAddChangeList(adapter.getSelectedItemsPositions());
            Receipt receipt = ReceiptApi.getReceipt(getApplicationContext(), Receipt.Type.SELL);
            if (receipt != null) {
                list.addAll(ChangesCreator.createAddChangeList(receipt.getPositions()));
            }
            (new OpenSellReceiptCommand(list, null)).process(context, future -> {
                try {
                    IntegrationManagerFuture.Result result = future.getResult();
                    if (result.getType() == IntegrationManagerFuture.Result.Type.OK) {
                        startActivity(NavigationApi.createIntentForSellReceiptEdit());
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setToolbar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle(R.string.app_name);
    }

    private void initRv() {
        RecyclerView recyclerView = findViewById(R.id.rv_recommendation);
        adapter = new RecommendationAdapter(new ArrayList<>(), position -> {
            ProductUi product = adapter.getItemAtPosition(position);
            product.setEnabled(!product.isEnabled());
            addButton.setEnabled(adapter.isAnyItemSelected());

        });

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<ProductUi> getFakeDataset() {
        List<ProductUi> list = new ArrayList<>();
        list.add(new ProductUi("Водочка Грей Гус", 1500));
        list.add(new ProductUi("Водочка Хорошая", 200));
        list.add(new ProductUi("Водочка Каждый День", 108));
        list.add(new ProductUi("Пиво Балтика", 70));
        list.add(new ProductUi("Сухарики Воронцовские", 30));
        list.add(new ProductUi("Пакет Маечка", 2));
        return list;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
