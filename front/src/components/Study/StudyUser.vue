<template>
  <div>

    <b-card-group columns>
      <b-card title="Card title that wraps to a new line"
              img-src="https://placekitten.com/g/400/450"
              img-fluid
              img-alt="image"
              img-top>
        <p class="card-text">
          This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit
          longer.
        </p>
      </b-card>
      <b-card header="Quote">
        <blockquote class="blockquote mb-0">
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
          <footer class="blockquote-footer">Someone famous in <cite title="Source Title">Source Title</cite></footer>
        </blockquote>
      </b-card>
      <b-card title="Title"
              img-src="https://placekitten.com/500/350"
              img-fluid
              img-alt="image"
              img-top>
        <p class="card-text">
          This card has supporting text below as a natural lead-in to additional content.
        </p>
        <small class="text-muted">Last updated 3 mins ago</small>
      </b-card>
      <b-card bg-variant="primary"
              text-variant="white">
        <blockquote class="card-blockquote">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.
          </p>
          <footer>
            <small>Someone famous in <cite title="Source Title">Source Title</cite></small>
          </footer>
        </blockquote>
      </b-card>
      <b-card title="Title">
        <p class="card-text">
          This card has supporting text below as a natural lead-in to additional content.
        </p>
        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
      </b-card>
      <b-card img-src="https://lorempixel.com/400/400/"
              img-fluid
              img-alt="image"
              overlay>
      </b-card>
      <b-card img-src="https://lorempixel.com/400/200/"
              img-fluid
              img-alt="image"
              img-top>
        <p class="card-text">
          This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content
          than the first.
        </p>
        <div slot="footer">
          <small class="text-muted">Footer Text</small>
        </div>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
  export default {
    props: ['study']
  }
</script>

<style scoped>

</style>
