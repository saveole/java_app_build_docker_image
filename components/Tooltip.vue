<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps({
  content: {
    default: "内容",
  },
  tip: {
    default: "提示语",
  },
})

const tipTxt = ref(props.tip)
const contentTxt = ref(props.content)
</script>

<template>
    <kbd>{{ contentTxt }}</kbd>
    <span class="tooltip-text"> {{ tipTxt }} </span>
</template>

<style>
.tooltip-text {
  visibility: hidden;
  position: absolute;
  display: inline-block;
  z-index: 1;
  color: #192733;
  background-color: rgba(253, 250, 250, 0.1);
  padding: 20px;
  top: 50%;
  left: 50%;
  border: #192733 1px dashed;
  border-radius: 2px;
}

kbd:hover ~ .tooltip-text {
  visibility: visible;
}
</style>
