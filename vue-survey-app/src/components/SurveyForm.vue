<template>
  <div class="container my-4">
    <!-- Survey Form -->
      <form @submit.prevent="handleSubmit" autocomplete="on" class="bg-white p-4 rounded shadow-sm">
        <!-- Greeting Message -->
        <div class="container my-3">
            <div v-if="form.username" id="greeting" class="alert alert-info" role="alert">
            Welcome back, {{ form.username }}!
            <a href="#" @click.prevent="resetForm">If you are not this user, please reset your name.</a>
            </div>
        </div>

        <!-- 1️ Personal Information -->
        <div class="mb-3">
            <label for="username" class="form-label required">Full Name</label>
            <input type="text" class="form-control" id="username" placeholder="Jane Doe" required autofocus v-model="form.username">
        </div>

        <div class="mb-3">
            <label for="street" class="form-label required">Street Address</label>
            <input type="text" class="form-control" id="street" required v-model="form.street">
        </div>
        <div class="row">
            <div class="col-md-4 mb-3">
                <label for="city" class="form-label required">City</label>
                <input type="text" class="form-control" id="city" required v-model="form.city">
            </div>

            <div class="col-md-4 mb-3">
                <label for="state" class="form-label required">State / Province</label>
                <input type="text" class="form-control" id="state" required v-model="form.state">
            </div>

            <div class="col-md-4 mb-3">
              <label for="zip" class="form-label required">ZIP / Postal Code</label>
              <input type="text" class="form-control" id="zip" pattern="\d{5}(-\d{4})?" placeholder="12345" required v-model="form.zip">
              <span class="text-danger small">{{ zipError }}</span>
            </div>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label required">Telephone</label>
            <input type="tel" class="form-control" id="phone" placeholder="123-456-7890" required v-model="form.phone">
        </div>

        <div class="mb-3">
            <label for="email" class="form-label required">Email</label>
            <input type="email" class="form-control" id="email" placeholder="name@example.com" required v-model="form.email">
        </div>

        <div class="mb-3">
            <label for="url" class="form-label">Personal Website / Portfolio (URL)</label>
            <input type="url" class="form-control" id="url" placeholder="https://example.com" v-model="form.url">
        </div>

        <div class="mb-3">
            <label for="date" class="form-label required">Date of Survey</label>
            <input type="date" class="form-control" id="date" required v-model="form.date">
        </div>
     
        <fieldset class="mb-4">
          <legend class="required">What did you like most about the campus?</legend>
          <div class="form-check form-check-inline" v-for="option in likeOptions" :key="option.value">
              <input class="form-check-input" type="checkbox" :id="option.value" :value="option.value" v-model="form.likes">
              <label class="form-check-label" :for="option.value">{{ option.label }}</label>
          </div>
        </fieldset>

        <fieldset class="mb-4">
          <legend class="required">How did you become interested in this university?</legend>
          <div class="form-check form-check-inline" v-for="option in sourceOptions" :key="option.value">
              <input class="form-check-input" type="radio" :id="option.value" :value="option.value" v-model="form.source" name="source">
              <label class="form-check-label" :for="option.value">{{ option.label }}</label>
          </div>
        </fieldset>

        <div class="mb-3">
        <label for="recommend" class="form-label required">Likelihood you would recommend this university</label>
        <select class="form-select" id="recommend" required v-model="form.recommend">
            <option disabled value="">Select…</option>
            <option value="very-likely">Very Likely</option>
            <option value="likely">Likely</option>
            <option value="unlikely">Unlikely</option>
        </select>
        </div>

        <!-- Comments -->
        <div class="mb-3">
          <label for="comments" class="form-label">Additional Comments</label>
          <textarea class="form-control" id="comments" rows="4" v-model="form.comments" placeholder="Your comments…"></textarea>
        </div>

    
        <button type="submit" class="btn btn-primary">Submit Survey</button>
        <button type="reset" class="btn btn-secondary" @click="resetForm">Reset Form</button>
      </form>
  </div>
</template>


<script>
export default {
  name: 'SurveyForm',
  data() {
    return {
      form: {
        username: '',
        street: '',
        zip: '',
        city: '',
        state: '',
        email: '',
        phone: '',
        url: '',
        date: '',
        likes: [],
        source: '',
        recommend: '',
        comments: ''
      },
      zipError: '',
      dataError: '',
      likeOptions: [
        { value: 'students', label: 'Students' },
        { value: 'location', label: 'Location' },
        { value: 'campus', label: 'Campus' },
        { value: 'atmosphere', label: 'Atmosphere' },
        { value: 'dorms', label: 'Dorm Rooms' },
        { value: 'sports', label: 'Sports' }
        ],
        sourceOptions: [
        { value: 'friends', label: 'Friends' },
        { value: 'television', label: 'Television' },
        { value: 'internet', label: 'Internet' },
        { value: 'other', label: 'Other' }
        ]
    }
  },
  mounted() {
    this.showGreeting()
  },
  methods: {
    showGreeting() {
      const name = this.getCookie("username")
      if (name) {
        this.form.username = name
      }
    },
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return decodeURIComponent(parts.pop().split(';').shift());
      return null;
    },
    setCookie(name, value, days = 7) {
      const expires = new Date(Date.now() + days * 864e5).toUTCString();
      document.cookie = `${name}=${encodeURIComponent(value)}; expires=${expires}; path=/`;
    },
    handleSubmit() {
        this.setCookie('username', this.form.username);

        // sent data to spring boot
        fetch('http://survey-backend-service:8081/api/surveys', {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.form)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); 
        })
        .then(data => {
            alert('Survey submitted successfully!');
            console.log('Response from server:', data);
        })
        .catch(error => {
            alert('Failed to submit survey.');
            console.error('Error submitting form:', error);
        });    
    },
    resetForm() {
      this.form = {
        username: '',
        street: '',
        zip: '',
        city: '',
        state: '',
        email: '',
        phone: '',
        url: '',
        date: '',
        likes: [],
        source: '',
        recommend: '',
        comments: ''
      };
    }
  }
}
</script>

<style scoped>
.required::after {
  content: " *";
  color: #dc3545;
}
</style>
