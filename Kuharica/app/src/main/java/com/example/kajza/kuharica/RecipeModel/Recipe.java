package com.example.kajza.kuharica.RecipeModel;


import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{


    public String name;
    public String image;
    public String description;
    public String instructions;
    public String category;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public Recipe() {

    }


    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String ingredient;
    public int amount;
    public String measureUnit;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.description);
        dest.writeString(this.instructions);
        dest.writeString(this.category);
        dest.writeString(this.result);
        dest.writeString(this.ingredient);
        dest.writeInt(this.amount);
        dest.writeString(this.measureUnit);
    }

    protected Recipe(Parcel in) {
        this.name = in.readString();
        this.image = in.readString();
        this.description = in.readString();
        this.instructions = in.readString();
        this.category = in.readString();
        this.result = in.readString();
        this.ingredient = in.readString();
        this.amount = in.readInt();
        this.measureUnit = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
