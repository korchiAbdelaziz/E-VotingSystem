import axios from 'axios';

const API_BASE_URLS = {
  voter: 'http://localhost:8081/api',
  vote: 'http://localhost:8083/api',
  result: 'http://localhost:8082/api',
};

// Configuration axios par défaut
axios.defaults.headers.common['Content-Type'] = 'application/json';

// Service pour Voter Service
export const voterService = {
  getAllElectors: () => axios.get(`${API_BASE_URLS.voter}/electors`),
  getElectorById: (id: number) => axios.get(`${API_BASE_URLS.voter}/electors/${id}`),
  createElector: (data: any) => axios.post(`${API_BASE_URLS.voter}/electors`, data),
  getElectorByIdentifiant: (identifiant: string) => 
    axios.get(`${API_BASE_URLS.voter}/electors/identifiant/${identifiant}`),
};

// Service pour Vote Service
export const voteService = {
  getAllVotes: () => axios.get(`${API_BASE_URLS.vote}/votes`),
  submitVote: (data: any) => axios.post(`${API_BASE_URLS.vote}/votes`, data),
  getVotesByCandidate: (candidateId: number) => 
    axios.get(`${API_BASE_URLS.vote}/votes/candidate/${candidateId}`),
};

// Service pour Result Service
export const resultService = {
  getResults: () => axios.get(`${API_BASE_URLS.result}/results`),
  getStatistics: () => axios.get(`${API_BASE_URLS.result}/results/statistics`),
  publishResults: () => axios.post(`${API_BASE_URLS.result}/results/publish`),
};

