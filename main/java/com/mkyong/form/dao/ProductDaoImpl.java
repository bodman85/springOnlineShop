package com.mkyong.form.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mkyong.form.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class ProductDaoImpl implements ProductDao {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Product findById(Integer id) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM products WHERE id=:id";

        Product result = null;
        try {
            result = namedParameterJdbcTemplate
                    .queryForObject(sql, params, new ProductMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return result;
    }

    @Override
    public List<Product> findAll() {

        String sql = "SELECT * FROM products";
        List<Product> result = namedParameterJdbcTemplate.query(sql, new ProductMapper());
        return result;

    }

    @Override
    public void save(Product product) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO PRODUCTS(NAME, CATEGORY, DESCRIPTION, PRICE) "
                + "VALUES ( :name, :category, :description, :price)";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(product), keyHolder);
        product.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Product product) {

        String sql = "UPDATE PRODUCTS SET NAME=:name, CATEGORY=:category, DESCRIPTION=:description, PRICE=:price"
                + " WHERE id=:id";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(product));
    }

    @Override
    public void delete(Integer id) {

        String sql = "DELETE FROM PRODUCTS WHERE id= :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private SqlParameterSource getSqlParameterByModel(Product product) {

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", product.getId());
        paramSource.addValue("name", product.getName());
        paramSource.addValue("category", product.getCategory());
        paramSource.addValue("description", product.getDescription());
        paramSource.addValue("price", product.getPrice());
        return paramSource;
    }

    private static final class ProductMapper implements RowMapper<Product> {

        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setCategory(rs.getString("category"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            return product;
        }
    }

}