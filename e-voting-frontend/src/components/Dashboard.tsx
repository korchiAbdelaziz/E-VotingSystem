import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { voterService, voteService, resultService } from '../services/api';
import './Dashboard.css';
import ElectorsPanel from './panels/ElectorsPanel';
import VotesPanel from './panels/VotesPanel';
import ResultsPanel from './panels/ResultsPanel';

const Dashboard: React.FC = () => {
  const { logout, isAdmin } = useAuth();
  const [activeTab, setActiveTab] = useState<'electors' | 'votes' | 'results'>('electors');
  const [stats, setStats] = useState({
    electors: 0,
    votes: 0,
    results: 0,
  });

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const [electorsRes, votesRes, resultsRes] = await Promise.all([
        voterService.getAllElectors(),
        voteService.getAllVotes(),
        resultService.getResults(),
      ]);
      
      setStats({
        electors: electorsRes.data?.length || 0,
        votes: votesRes.data?.length || 0,
        results: resultsRes.data?.length || 0,
      });
    } catch (error) {
      console.error('Erreur lors du chargement des statistiques:', error);
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>🗳️ E-Voting System - Administration</h1>
          <div className="header-actions">
            <span className="admin-badge">Super Admin</span>
            <button onClick={logout} className="logout-button">
              Déconnexion
            </button>
          </div>
        </div>
      </header>

      <div className="dashboard-stats">
        <div className="stat-card">
          <div className="stat-icon">👥</div>
          <div className="stat-info">
            <div className="stat-value">{stats.electors}</div>
            <div className="stat-label">Électeurs</div>
          </div>
        </div>
        <div className="stat-card">
          <div className="stat-icon">✅</div>
          <div className="stat-info">
            <div className="stat-value">{stats.votes}</div>
            <div className="stat-label">Votes</div>
          </div>
        </div>
        <div className="stat-card">
          <div className="stat-icon">📊</div>
          <div className="stat-info">
            <div className="stat-value">{stats.results}</div>
            <div className="stat-label">Résultats</div>
          </div>
        </div>
      </div>

      <div className="dashboard-tabs">
        <button
          className={`tab-button ${activeTab === 'electors' ? 'active' : ''}`}
          onClick={() => setActiveTab('electors')}
        >
          👥 Électeurs
        </button>
        <button
          className={`tab-button ${activeTab === 'votes' ? 'active' : ''}`}
          onClick={() => setActiveTab('votes')}
        >
          ✅ Votes
        </button>
        <button
          className={`tab-button ${activeTab === 'results' ? 'active' : ''}`}
          onClick={() => setActiveTab('results')}
        >
          📊 Résultats
        </button>
      </div>

      <div className="dashboard-content">
        {activeTab === 'electors' && <ElectorsPanel onUpdate={loadStats} />}
        {activeTab === 'votes' && <VotesPanel onUpdate={loadStats} />}
        {activeTab === 'results' && <ResultsPanel onUpdate={loadStats} />}
      </div>
    </div>
  );
};

export default Dashboard;

