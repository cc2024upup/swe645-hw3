<template>
  <div class="container my-4">
    <h2 class="mb-4">Submitted Surveys</h2>
    <div v-if="surveys.length === 0">No surveys submitted yet.</div>

    <table class="table table-striped" v-else>
      <thead>
        <tr>
          <th>Name</th>
          <th>Address</th>
          <th>City</th>
          <th>State</th>
          <th>ZIP</th>
          <th>Phone</th>
          <th>Email</th>
          <th>Survey Date</th>
          <th>Liked Most</th>
          <th>Source</th>
          <th>Recommend</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(survey, index) in surveys" :key="survey.id">
          <td>{{index+1}}</td>
          <td>
            <input v-model="survey.username" class="form-control mb-1" placeholder="Full Name">
          </td>
          <td>{{ survey.streetAddress }}</td>
          <td><input v-model="survey.city" class="form-control"></td>
          <td><input v-model="survey.state" class="form-control"></td>
          <td><input v-model="survey.zip" class="form-control"></td>
          <td><input v-model="survey.phone" class="form-control"></td>
          <td><input v-model="survey.email" class="form-control"></td>
          <td><input type="date" v-model="survey.date" class="form-control"></td>
          <td>
            <ul>
              <li v-for="like in survey.likedMost" :key="like">{{ like }}</li>
            </ul>
          </td>
          <td>{{ survey.source }}</td>
          <td>
            <select v-model="survey.recommend" class="form-select">
              <option value="very-likely">Very Likely</option>
              <option value="likely">Likely</option>
              <option value="unlikely">Unlikely</option>
            </select>
          </td>
          <td>
            <button class="btn btn-success btn-sm me-2" @click="updateSurvey(survey)">Save</button>
            <button class="btn btn-danger btn-sm" @click="deleteSurvey(survey.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SurveyList',
  data() {
    return {
      surveys: []
    }
  },
  created() {
    this.fetchSurveys()
  },
  methods: {
    async fetchSurveys() {
      try {
        const response = await axios.get('http://localhost:8081/api/surveys')
        this.surveys = response.data
      } catch (error) {
        console.error('Error fetching surveys:', error)
      }
    },
    async updateSurvey(survey) {
      try {
        await axios.put(`http://localhost:8081/api/surveys/${survey.id}`, survey)
        alert('Survey updated successfully')
      } catch (error) {
        console.error('Update error:', error)
        alert('Failed to update survey')
      }
    },
    async deleteSurvey(id) {
      if (!confirm('Are you sure you want to delete this survey?')) return
      try {
        await axios.delete(`http://localhost:8081/api/surveys/${id}`)
        this.surveys = this.surveys.filter(s => s.id !== id)
      } catch (error) {
        console.error('Delete error:', error)
        alert('Failed to delete survey')
      }
    }
  }
}
</script>

<style scoped>
input.form-control, select.form-select {
  width: 140px;
  font-size: 0.85rem;
  padding: 0.25rem;
}
</style>
