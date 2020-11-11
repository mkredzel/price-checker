<template>
  <section class="pricing-section spad">
    <div class="container" id="searchSection">
      <div class="row">
        <div class="section-title normal-title">
          <h3>Search Televisions</h3>
          <br />
          <form class="form-inline mr-auto">
            <b-form-input
              class="form-control mr-sm-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
              name="searchInput"
              id="searchInput"
              v-model="searchQuery"
            />
            <button
              class="btn btn-outline-primary btn-rounded"
              id="searchBtn"
              @click="search()"
            >
              Search
            </button>
          </form>
        </div>
      </div>

      <div
        class="container"
        id="tvs"
        :current-page="currentPage"
        :per-page="perPage"
      >
        <div class="row">
          <div
            v-for="(tv, index) in tvs.slice(
              8 * (currentPage - 1),
              8 * currentPage
            )"
            :key="index"
            class="col-lg-3 col-md-12 col-sm-12"
          >
            <div class="pricing__item" id="item">
              <img
                :srcset="tv.image_url"
                width="200"
                height="200"
                alt="product"
              />
              <h3>{{ tv.brand }} {{ tv.screen_size }}</h3>
              <ul>
                <h4 id="fitText">{{ tv.model }}</h4>
                <h5 id="fitText">{{ tv.description }}</h5>
              </ul>
              <a
                class="primary-btn"
                @click="indexOfChosenTV = tv.id"
                v-b-modal.modal-comparison
                >Compare Prices</a
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <b-modal id="modal-comparison" title="Price Comparison" hide-footer>
      <v-table
        class="table"
        id="comparisons"
        :data="comparisons"
        :hideSortIcons="true"
      >
        <thead slot="head">
          <v-th>Website</v-th>
          <v-th sortKey="price" defaultSort="asc">Price</v-th>
          <v-th>Link</v-th>
        </thead>
        <tbody slot="body" slot-scope="{ displayData }">
          <tr v-for="row in displayData" :key="row.guid">
            <td
              v-if="row.tv_id == indexOfChosenTV && row.url.includes('amazon')"
            >
              <a href="https://www.amazon.co.uk/">Amazon</a>
            </td>
            <td
              v-else-if="
                row.tv_id == indexOfChosenTV && row.url.includes('sonic')
              "
            >
              <a href="https://www.sonicdirect.co.uk/">Sonic Direct</a>
            </td>
            <td
              v-else-if="
                row.tv_id == indexOfChosenTV && row.url.includes('hughes')
              "
            >
              <a href="https://www.hughes.co.uk/">Hughes</a>
            </td>
            <td
              v-else-if="
                row.tv_id == indexOfChosenTV && row.url.includes('worldtv')
              "
            >
              <a href="https://www.electronicworldtv.co.uk/"
                >Electronic World TV</a
              >
            </td>
            <td v-else-if="row.tv_id == indexOfChosenTV">
              <a href="https://www.appliancesdirect.co.uk/"
                >Appliances Direct</a
              >
            </td>
            <td v-if="row.tv_id == indexOfChosenTV">£{{ row.price }}</td>
            <td v-if="row.tv_id == indexOfChosenTV">
              <a :href="row.url" class="primary-btn">Redirect Me</a>
            </td>
          </tr>
        </tbody>
      </v-table>
    </b-modal>

    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      first-text="⏮"
      prev-text="⏪"
      next-text="⏩"
      last-text="⏭"
      aria-controls="tvs"
      align="center"
      size="lg"
    ></b-pagination>
  </section>
</template>

<script>
import Api from "../../services/api";
import $ from "jquery";
export default {
  name: "TV",
  data() {
    return {
      perPage: 8,
      currentPage: 1,
      tvs: [],
      comparisons: [],
      indexOfChosenTV: 0,
      searchQuery: "",
    };
  },
  methods: {
    search() {
      let searchValue = document.getElementById("searchInput").value;
      Api()
        .get("/tvs/search?searchInput=" + searchValue)
        .then((res) => (this.tvs = res.data));
    },
    getTVs() {
      Api()
        .get("/tvs")
        .then((res) => (this.tvs = res.data));
    },
    getComparison() {
      Api()
        .get("/comparison")
        .then((res) => (this.comparisons = res.data));
    },
    showModal() {
      let element = this.$refs.modal.$el;
      $(element).modal("show");
    },
  },
  mounted() {
    this.getComparison();
    this.getTVs();
  },  
  computed: {
    rows() {
      return this.tvs.length;
    },
  },
};
</script>