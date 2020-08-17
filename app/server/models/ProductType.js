const Sequelize = require("sequelize");
const sequelize = require("../database/connection");

const ProductType = sequelize.define(
  "Product_type",
  {
    id: {
      type: Sequelize.INTEGER(11),
      allowNull: false,
      autoIncrement: true,
      primaryKey: true
    },
    title: Sequelize.STRING(250),
    description: Sequelize.TEXT
  },
  {
    timestamps: false,
  }
);
module.exports = ProductType;

