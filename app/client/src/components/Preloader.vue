<template>
  <div id="preloder">
    <div class="loader"></div>
  </div>
</template>
 
<script>
import $ from 'jquery'
export default {
  name: "Preloader"
}
$(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");
    });
</script> 