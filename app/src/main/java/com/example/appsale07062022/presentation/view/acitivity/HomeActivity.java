package com.example.appsale07062022.presentation.view.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale07062022.R;
import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.Cart;
import com.example.appsale07062022.data.model.Product;
import com.example.appsale07062022.databinding.ActivityHomeBinding;
import com.example.appsale07062022.presentation.view.adapter.ProductAdapter;
import com.example.appsale07062022.presentation.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    ActivityHomeBinding binding;
    ProductAdapter productAdapter;
    TextView tvCountCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(HomeActivity.this);
            }
        }).get(HomeViewModel.class);

        initData();
        observerData();
        event();
    }

    private void initData() {
        binding.toolbarHome.setTitle("Food");
        binding.toolbarHome.setTitleTextColor(getResources().getColor(R.color.primary, null));
        setSupportActionBar(binding.toolbarHome);

        productAdapter = new ProductAdapter();
        binding.recyclerViewProduct.setAdapter(productAdapter);
        binding.recyclerViewProduct.setHasFixedSize(true);
    }

    private void event() {
        homeViewModel.getProducts();
        homeViewModel.getCart();

        productAdapter.setOnItemClickFood(new ProductAdapter.OnItemClickProduct() {
            @Override
            public void onClick(int position) {
                homeViewModel.addCart(productAdapter.getListProducts().get(position).getId());
            }
        });
    }

    private void observerData() {
        homeViewModel.getResourceListProducts().observe(this, new Observer<AppResource<List<Product>>>() {
            @Override
            public void onChanged(AppResource<List<Product>> listAppResource) {
                switch (listAppResource.status) {
                    case SUCCESS:
                        productAdapter.updateListProduct(listAppResource.data);
                        setLoading(false);
                        break;
                    case LOADING:
                        setLoading(true);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, listAppResource.message, Toast.LENGTH_SHORT).show();
                        setLoading(false);
                        break;
                }
            }
        });

        homeViewModel.getResourceCart().observe(this, new Observer<AppResource<Cart>>() {
            @Override
            public void onChanged(AppResource<Cart> cartAppResource) {
                switch (cartAppResource.status) {
                    case SUCCESS:
                        setupBadge(getQuantity(cartAppResource.data.getProducts()));
                        setLoading(false);
                        break;
                    case LOADING:
                        setLoading(true);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, cartAppResource.message, Toast.LENGTH_SHORT).show();
                        setLoading(false);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);
        setupBadge(0);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }

    private void setLoading(Boolean isLoading) {
        if (isLoading) {
            binding.layoutLoading.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutLoading.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getQuantity(List<Product> productList) {
        if (productList == null || productList.size() == 0) {
            return 0;
        }
        int totalQuantities = 0;
        for (Product product: productList) {
            totalQuantities += product.getQuantity();
        }
        return totalQuantities;
    }

    private void setupBadge(int quantities) {
        if (quantities == 0) {
            tvCountCart.setVisibility(View.GONE);
        } else {
            tvCountCart.setVisibility(View.VISIBLE);
            tvCountCart.setText(String.valueOf(Math.min(quantities, 99)));
        }
    }
}
