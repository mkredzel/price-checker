<template>
  <section class="pricing-section spad" >
    <div class="container" id="searchSection">
      <div class="row">
        <div class="section-title normal-title">
          <h3>Search your product</h3>
          <br>
          <form class="form-inline mr-auto" action="/tvs/search">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchInput" id="searchInput">
            <button class="btn btn-outline-primary btn-rounded" type="submit" id="searchBtn" v-on:click="displayTVS()">Search</button>
          </form>
        </div>
      </div>
      <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" first-text="⏮" prev-text="⏪" next-text="⏩" last-text="⏭" aria-controls="tvs" align="center" size="lg"></b-pagination>
      <div class="container" id="tvs" :current-page="currentPage" :per-page="perPage">
        <div class="row">
          <div v-for="(tv, index) in tvs.slice(8*(currentPage-1),8*(currentPage))" :key="index" class="col-lg-3 col-md-12 col-sm-12">
            <div class="pricing__item" id='item'>
              <img :srcset="tv.image_url" width="200" height="200" alt="product">
              <h3>£{{tv.price.amount}}</h3>
              <ul>
                <h4 id="fitText">{{tv.Product_type.title}}</h4>
                <h5 id="fitText">{{tv.Product_type.description}}</h5>
              </ul>
              <a :href="tv.direct_url" class="primary-btn">Redirect Me</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import Api from '../../services/api';
export default {
	name: 'TV',
	methods: {
    displayTVS() {
      let searchValue = document.getElementById('searchInput').value;
      Api().get("/tvs/search?searchInput=" + searchValue).then(res => (this.tvs = res.data));
    }
  },
	mounted() {
		Api().get("/tvs").then(res => (this.tvs = res.data));
	},
	data() {
		return {
			perPage: 8,
			currentPage: 1,
      tvs: []
		}
	},
	computed: {
      rows() {
        return this.tvs.length
      }
    }
}
</script>