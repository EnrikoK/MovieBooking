<template>
<h1>Filmid</h1>
<div class="content-area">
<screening-list ></screening-list>  
</div>

</template>

<script>
import ScreeningList from "@/components/ScreeningList"
export default {
  
  components: {
    'screening-list':ScreeningList
  }
}
</script>

<style>
.content-area{
  margin-left: 5em;
  margin-right: 5em;
}
</style>