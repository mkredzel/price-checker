<template>
    <section class="hero-section">
        <div class="" id="landing">
            <div class="container" >
                <div class="row">
                    <div class="col-lg-6">
                        <div class="hero__text">
                            <h5>Welcome to TV Price Checker!</h5>
                            <h2>Compare television prices from all over the internet!</h2>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="hero__img">
                            <img src="../assets/img/hero-right2.png">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
export default {
  name: 'Landing',
  
}
</script>

<style scoped>
#landing {
    background-image: url('../assets/img/hero-1.jpg');
    background-repeat: no-repeat;
    background-size: 100% 100%;
}

.hero__text {
    color: white;
    margin-top: 200px;
}

.hero__img {
    margin-top: 170px;
}
</style>