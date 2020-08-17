const Sequelize = require("sequelize");
const sequelize = require("../database/connection");

const TV = sequelize.define(
  "tvs",
  {
    id: {
      type: Sequelize.INTEGER(11),
      allowNull: false,
      autoIncrement: true,
      primaryKey: true
    },
    product_type_id: Sequelize.INTEGER(11),
    price_id: Sequelize.INTEGER(11),
    image_url: Sequelize.STRING(2083),
    direct_url: Sequelize.STRING(2083),
  },
  {
    timestamps: false,
  },
);
module.exports = TV;
