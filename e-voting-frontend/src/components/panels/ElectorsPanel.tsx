import React, { useState, useEffect } from 'react';
import { voterService } from '../../services/api';
import './Panel.css';

interface Elector {
  idElector: number;
  nom: string;
  prenom: string;
  dateNaissance: string;
  identifiantSecurise: string;
  aVote: boolean;
}

interface ElectorsPanelProps {
  onUpdate: () => void;
}

const ElectorsPanel: React.FC<ElectorsPanelProps> = ({ onUpdate }) => {
  const [electors, setElectors] = useState<Elector[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    nom: '',
    prenom: '',
    dateNaissance: '',
    identifiantSecurise: '',
  });

  useEffect(() => {
    loadElectors();
  }, []);

  const loadElectors = async () => {
    try {
      setLoading(true);
      const response = await voterService.getAllElectors();
      setElectors(response.data);
      setError('');
    } catch (err: any) {
      let errorMessage = 'Erreur lors du chargement des électeurs';
      
      if (err.code === 'ERR_NETWORK' || err.message === 'Network Error') {
        errorMessage = '❌ Erreur réseau : Le service backend n\'est pas accessible. Vérifiez que voter-service est démarré sur le port 8081.';
      } else if (err.response) {
        errorMessage = 'Erreur lors du chargement: ' + (err.response.data?.message || err.message);
      } else {
        errorMessage = 'Erreur lors du chargement: ' + (err.message || 'Erreur inconnue');
      }
      
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await voterService.createElector({
        ...formData,
        dateNaissance: new Date(formData.dateNaissance).toISOString().split('T')[0],
      });
      setShowForm(false);
      setFormData({ nom: '', prenom: '', dateNaissance: '', identifiantSecurise: '' });
      loadElectors();
      onUpdate();
    } catch (err: any) {
      let errorMessage = 'Erreur lors de la création';
      
      if (err.code === 'ERR_NETWORK' || err.message === 'Network Error') {
        errorMessage = '❌ Erreur réseau : Le service backend n\'est pas accessible. Vérifiez que :\n' +
          '1. Le service voter-service est démarré (port 8081)\n' +
          '2. MySQL est démarré\n' +
          '3. Eureka Server est démarré (port 8761)';
      } else if (err.response) {
        errorMessage = err.response.data?.message || err.response.data || err.message;
      } else {
        errorMessage = err.message || 'Erreur inconnue';
      }
      
      setError(errorMessage);
    }
  };

  if (loading) {
    return <div className="panel-loading">Chargement...</div>;
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <h2>Gestion des Électeurs</h2>
        <button onClick={() => setShowForm(!showForm)} className="add-button">
          {showForm ? 'Annuler' : '+ Ajouter un électeur'}
        </button>
      </div>

      {showForm && (
        <form onSubmit={handleSubmit} className="panel-form">
          <div className="form-row">
            <div className="form-group">
              <label>Nom</label>
              <input
                type="text"
                value={formData.nom}
                onChange={(e) => setFormData({ ...formData, nom: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Prénom</label>
              <input
                type="text"
                value={formData.prenom}
                onChange={(e) => setFormData({ ...formData, prenom: e.target.value })}
                required
              />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Date de naissance</label>
              <input
                type="date"
                value={formData.dateNaissance}
                onChange={(e) => setFormData({ ...formData, dateNaissance: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Identifiant sécurisé</label>
              <input
                type="text"
                value={formData.identifiantSecurise}
                onChange={(e) => setFormData({ ...formData, identifiantSecurise: e.target.value })}
                required
              />
            </div>
          </div>
          <button type="submit" className="submit-button">Créer</button>
        </form>
      )}

      {error && <div className="panel-error">{error}</div>}

      <div className="panel-table-container">
        <table className="panel-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nom</th>
              <th>Prénom</th>
              <th>Date de naissance</th>
              <th>Identifiant</th>
              <th>Statut</th>
            </tr>
          </thead>
          <tbody>
            {electors.length === 0 ? (
              <tr>
                <td colSpan={6} className="empty-state">Aucun électeur trouvé</td>
              </tr>
            ) : (
              electors.map((elector) => (
                <tr key={elector.idElector}>
                  <td>{elector.idElector}</td>
                  <td>{elector.nom}</td>
                  <td>{elector.prenom}</td>
                  <td>{new Date(elector.dateNaissance).toLocaleDateString('fr-FR')}</td>
                  <td>{elector.identifiantSecurise}</td>
                  <td>
                    <span className={`status-badge ${elector.aVote ? 'voted' : 'not-voted'}`}>
                      {elector.aVote ? '✅ A voté' : '⏳ En attente'}
                    </span>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ElectorsPanel;

